# webapp.whiteboard

Ce petit projet est destiné à expérimenter Pax Web Whiteboard.
Il s'inspire de http://www.ayomaonline.com/web-developing/modular-web-applications-with-osgi-part-1-modules-and-dynamic-menus/

Le but rechercher est de définir une architecture permettant de déployer un module dans une web application, simplement en ajoutant un bundle dans le conteneur OSGI.
L'application devant alors automatiquement reconnaitre le module et le rendre accessible à l'utilisateur.

Le fonctionnement retenu pour ce prototype est des plus simple.
L'application expose une JSP qui affiche un menu.
Par défaut celui-ci est vide.

Lorsqu'un module est détecté, les éléments de menu qu'il définit sont ajoutés par la JSP.
Bien sûr cet exemple basique a vocation à être généralisé.

Plus que la définition d'un menu c'est la mécanique d’échange entre l'application et les modules qui est recherché.
Le projet va donc se découper en plusieurs sous-projets.

* main-api définit le service d'échange de définition de menu 
* module-whiteboard définit un tableau blanc générique qui sera instancié par l'application avec l'api de menu.
* Le module main définit l'application elle-même. J'ai choisi d'en faire un module, car je pense que n'importe quel module de l'application doit pouvoir instancier un tableau blanc pour travailler avec les autres.
* Le module user est un simple module qui définit deux servlets et les deux entrées de menu pour y accéder.
* Le module message est un simple module qui définit quatre servlets et les quatre entrées de menu pour y accéder.

Pour qu'un module soit fonctionnel, il faut qu'il ajoute dans le contexte de l'application ses propres ressources, servlets etc.
Tout cela se fait via blueprint.

    <service interface="org.ops4j.pax.web.extender.whiteboard.HttpContextMapping">
        <bean
            class="org.ops4j.pax.web.extender.whiteboard.runtime.DefaultHttpContextMapping">
            <property name="httpContextId" value="httpContext" />
            <property name="path" value="${web.context}" />
        </bean>
    </service>

    <service interface="javax.servlet.Servlet">
        <service-properties>
            <entry key="alias" value="/${module.name}/add" />
            <entry key="httpContext.id" value="httpContext" />
        </service-properties>
        <bean class="fr.sekaijin.osgi.web.module.user.servlet.Add" />
    </service>

Un très bon exemple se trouve ici https://github.com/ops4j/org.ops4j.pax.web/blob/master/samples/whiteboard-blueprint/src/main/resources/OSGI-INF/blueprint/blueprint.xml
Il convient de faire attention à un détail dans celui-ci. il utilise le web-contextPath /
Pour utiliser un autre contexte, il est nécessaire de rajouter `<entry key="httpContext.id" value="httpContext" />` comme ci-dessus.

Enfin l'essentiel du projet. Le module implémente l'interface débine dans main-api.

    public class UserModule implements IModule {

        protected String moduleName;

        public UserModule(String moduleName) {
            super();
            this.moduleName = moduleName;
        }

        public String getModuleName() {
            return moduleName;
        }

        public List<MenuItem> getMenuItems() {
            List<MenuItem> menuItemList = new ArrayList<>();

            menuItemList.add(new MenuItem(moduleName, "user_add.png", "add", "add"));
            menuItemList.add(new MenuItem(moduleName, "users.jpeg", "manage", "manage"));
            return menuItemList;
        }
    }

Dans blueprint cette classe est exposé comme service

    <service interface="fr.sekaijin.osgi.web.main.api.IModule">
        <bean class="fr.sekaijin.osgi.web.module.user.UserModule">
            <argument type="java.lang.String" value="${module.name}" />
        </bean>
    </service>

L'application dans le module main démarre un tableau blanc pour capter l'exposition de ces services.
    
    <bean class="fr.sekaijin.osgi.web.module.tracker.ModuleTracker" init-method="start" destroy-method="close">
        <argument type="org.osgi.framework.BundleContext" ref="blueprintBundleContext" />
        <argument type="java.lang.Class" value="fr.sekaijin.osgi.web.main.api.IModule" />
        <argument type="java.lang.Class" value="fr.sekaijin.osgi.web.main.tracker.ModuleTrackerCustomizer" />
    </bean>

À chaque module qui déclare un menu le Tracker place cette définition dans un registre.
Et la JSP s'en sert pour l'afficher.
    