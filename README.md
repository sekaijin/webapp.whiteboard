# webapp.whiteboard

Ce petit projet est destiné à expérimenter Pax Web Whiteboard.
Il s'inspire de http://www.ayomaonline.com/web-developing/modular-web-applications-with-osgi-part-1-modules-and-dynamic-menus/

Le but rechercher est de définir une architecture permettant de déployer un module dans une web application, simplement en ajoutant un bundle dans le conteneur OSGI.
L'application devant alors automatiquement reconnaitre le module et le rendre accessible à l'utilisateur.

Le fonctionnement retenu pour ce prototype est des plus simple.
L'application expose une JSP qui affiche un menu.
Par défaut celui-ci est vide.

Lorsqu'un module est détecté, les éléments de menu qu'il définit sont ajoutés.
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
Un très bon exemple se trouve ici https://github.com/ops4j/org.ops4j.pax.web/blob/master/samples/whiteboard-blueprint/src/main/resources/OSGI-INF/blueprint/blueprint.xml
Pour simplifier les dev le bundle module-whiteboard offre une définition gloable du module.

    <service interface="fr.sekaijin.osgi.web.module.IModule">
        <bean class="fr.sekaijin.osgi.web.module.Module">
            <property name="contextId" value="httpContext" />
            <property name="moduleName" value="${module.name}" />
            <property name="contextPath" value="${web.context}" />
            <property name="servlets">
                <map>
                    <entry key="add">
                        <bean class="fr.sekaijin.osgi.web.module.user.servlet.Add" />
                    </entry>
                    <entry key="manage">
                        <bean class="fr.sekaijin.osgi.web.module.user.servlet.Manage" />
                    </entry>
                </map>
            </property>
        </bean>
    </service>
Il va invoquer les services pax whiteboard en ajoutant tout le nécessaire.
Enfin l'essentiel du projet. Le module implémente l'interface définie dans main-api.
La encore la définition se fait dans blueprint.

    <service interface="fr.sekaijin.osgi.web.main.api.IMenu">
        <bean class="fr.sekaijin.osgi.web.main.api.Menu">
            <property name="moduleName" value="${module.name}" />
             <property name="menuItems">
                <list>
                    <bean class="fr.sekaijin.osgi.web.main.api.MenuItem">
                        <property name="icon" value="user_add.png" />
                        <property name="label" value="add" />
                        <property name="path" value="add" />
                    </bean>
                    <bean class="fr.sekaijin.osgi.web.main.api.MenuItem">
                        <property name="icon" value="users.jpeg" />
                        <property name="label" value="manage" />
                        <property name="path" value="manage" />
                    </bean>
                </list>
             </property>
        </bean>
    </service>

L'application dans le module main démarre un tableau blanc pour capter l'exposition de ces services.
    
    <bean class="fr.sekaijin.osgi.web.module.tracker.ModuleTracker" init-method="start" destroy-method="close">
        <argument type="org.osgi.framework.BundleContext" ref="blueprintBundleContext" />
        <argument type="java.lang.Class" value="fr.sekaijin.osgi.web.main.api.IModule" />
        <argument type="java.lang.Class" value="fr.sekaijin.osgi.web.main.tracker.ModuleTrackerCustomizer" />
    </bean>

À chaque module qui déclare un menu le Tracker place cette définition dans un registre.
Une webScocket notifie le client que le contenu du registre à changé pour qui recharge la définition.
    
