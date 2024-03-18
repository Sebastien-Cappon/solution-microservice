# Green code : de l'intention à l'action

*Depuis les années 90, l'impact environnemental de l'informatique est devenu une source de préoccupations. La principale cible de celles-ci était le hardware. Au milieu de la première décennie des années 2000, les infrastructures sont ciblées à leur tour. Il faudra attendre encore 15 ans environ avant que l'on ne considère plus que les infrastructures en elles-mêmes, mais leurs contenus et implicitement, pour nous, développeurs : le code.*

## Les fondamentaux du Green Code

### Origines et enjeux

Le ***"Green Code"***, ou code responsable, est l'un des derniers nés d'un mouvement écologique plus large appelé ***"Green IT"***, ou encore, ***"Informatique Responsable"***. Il est intéressant de noter que si la genèse du ***"Green IT"*** prend place aux État-Unis, probablement avec le programme Energy Star, ces notions sont aujourd'hui largement appliquées, promues, développées et améliorées en France, bon élève de l'écologie à l'échelle mondiale. Il n'est donc en rien surprenant d'y voir émerger divers associations importantes telles **The Shift Project (2010)**, l'**Alliance Green IT (2011)**, l'**INR - Institut du numérique Responsable (2018)**.

Le ***"Green Code"*** incite à la prise en compte des impacts environnementaux et, intrinsèquement, économiques du développement logiciel. Si les leviers d'actions sont nombreux et se placent à tous les niveaux du développement d'une application, ils se regroupent sous 3 objectifs plus généraux :
- Éliminer le surpoids numérique des applications.
- Réduire les ressources nécessaires au développement et au fonctionnement des applications.
- Diminuer la pollution générée par les applications pendant toutes les étapes de leur vie.

### Principales recommandations

Les 3 objectifs sus-cités restent vagues. En eux-mêmes, ils ne signifient pas grand-chose. "Éliminer le surpoids numérique" reste assez clair et évident. Mais "réduire les ressources nécessaires" est déjà plus flou. Comment ? À quel niveau ? Quant à *"Diminuer la pollution générée"*... La notion est obscure. Toutefois, il est possible de trouver, çà et là, **quelques recommandations**, regroupées ci-après :

- **Éliminer le surpoids numérique des applications :**
  - Choisir et distribuer des média légers et adaptés au périphérique employé : Responsive.
  - Charger les ressources uniquement lorsqu'elles sont nécessaires : On-Demand, Lazy loading...
  - Minimifier le code avant la mise en production.

- **Réduire les ressources nécessaires au développement et au fonctionnement des applications :**
  - Créer des algorithmes efficaces et directs.
  - Privilégier les fonctions natives aux dépendances externes.
  - Considérer les dépendances utilisées : leur poids, leur accessibilité.
  - Ne charger que les dépendances nécessaires.
  - Diminuer les requêtes serveur.
  - Penser et optimiser ses bases de données.
  - Refactorer et documenter son code pour améliorer la maintenabilité.

- **Diminuer la pollution générée par les applications pendant toutes les étapes de leur vie :**
  - Contrôler la consommation par le choix d'un hébergeur écoresponsable.
  - Opter pour une infrastructure peu énergivore.

## Mise en application du Green Code

*Dans le cadre du projet actuel, les recommandations des premiers et troisièmes groupes ci-dessus sont inenvisageables. L'application en micro-services créée est une maquette. Nous ne distribuons (quasiment) aucun média. Nous ne passons pas l'application en production. Nous ne sommes donc pas concernés par le choix d'un hébergeur et d'une structure. Toutefois, nous pouvons considérer les recommandations du second groupe.*

### Créer des algorithmes efficaces et directs

Nous avons essentiellement affaire ici à des API CRUD. Leur simplicité est extrême. Elles reposent principalement sur *JpaRepository* et *MongoRepository* et ne sont donc que des requêtes auprès de ces interfaces. Seul le service RiskService du micro-service *T2D2Diabetes* échappe à cette généralité. Toutefois, il n'est constitué que d'une regex, d'un calcul de moyenne et de quelques conditions. Il serait difficile, présentement, de faire plus simple. En revanche, notre Front, sous *Angular*, pourrait probablement être allégé et simplifié. Certains de ses algorithmes sont retors, principalement à cause d'un manque de connaissances en *JS* de ma part.

### Privilégier les fonctions natives aux dépendances externes

Très sincèrement, il m'a fallu quelques recherches appronfondies pour comprendre ce que pouvait être des *"fonctions natives"* en Java. Il existerait plusieusrs interfaces nommées *JNI*, *JNR* et *JNA* qui seraient effectivement des solutions des plus rapides en *Java*. Toutefois, à l'exception de *JNA* qui semble tirer sont épingle du jeu, elles sont reconnues comme étant difficiles d'utilisation, difficiles à debug, potentiellement lourdes en termes de performances et peu fiable selon l'expertise du développeur. Les trois nécessitent un apprentissage complémentaire et réduisent donc la maintenabilité du code. On peut alors considérer qu'il s'agit, dans le cas présent, de sur-optimisation, ce qui, généralement, expose à plus de problèmes qu'elle n'en résout.
Toutefois, si par *"privilégier les fonctions natives"*, on entend *"Éviter d'appeler des dépendances loufoques là où on peut appeler des méthodes Java ou Spring" (notre application reposant sur SpringBoot)*, alors oui, évidemment que oui, il le faut. Et c'est le cas ici.

### Considérer les dépendances utilisées

Ce point rejoint le précédent. Si d'aventure nous sommes contraint de faire appel à une dépendance spécifique, comme Eureka pour le service discovery *(T2D2DiscoveryService)*, par exemple, il est judicieux de se poser la question de son efficacité et d'envisager, si nécessaire, une alternative plus optimisée.

### Ne charger que les dépendances nécessaires

Cela va de soi et c'est le cas dans nos présentes API. Notre front pourrait faire l'objet d'une vérification sur ce point, bien que j'ai veillé au maximum à ne pas faire appel à ce dont je n'ai pas besoin. Pas par soucis d'écologie *(nous y reviendrons plus bas)*, mais par soucis de simplicité, d'organisation et de maintenabilité.

### Dimminuer les requêtes serveur

Encore une fois, la présente application est succincte. Toute requête est justifiée. On pourrait éventuellement stocker certaines informations et ne les recharger qu'à la demande, au lieu d'envoyer une requête le serveur à chaque "changement de page". C'est le cas des listes : patients, triggers. Mais ce serait au dépens de l'expérience utilisateur et en débit du bon sens. La tendance actuelle serait plutôt de rafraîchir ces listes au bout d'un temps donné afin de s'assurer qu'elles sont toujours à jour, même après un temps d'inactivité de la part de l'utilisateur. Ceci augmenterait implicitement le nombre de requêtes au serveur, mais améliorerait l'expérience de l'utilisateur.

### Penser et optimiser ses bases de données

Éviter d'employer une base de donnée non-relationnelle *(ici, MongoDB)* là où une base de donnée relationnelle ferait le même travail plus efficacement est un bon moyen d'optimiser ses bases de données. Ici, le volume de données est faible et les relations sont importantes. MongoDB n'a pas sa place. MongoDB est lent en lecture et nous n'écrivons que peu de données simultanément là où il est utilisé *(T2D2Note)*.

### Refactorer et documenter son code pour améliorer la maintenabilité

Le B-A-BA du développement. Que dire de plus ? L'application actuelle l'est afin d'assurer une meilleure maintenabilité. Moins de temps à comprendre et reprendre le code = moins de ressources consommées.

## Critique du Green Code et de l'informatique responsable

On ne peut aborder le ***"Green Code"*** sans en dénoncer l'absurdité. Ce sont de jolis principes, mais ne résultent-ils pas plus d'une forme de mercantilisme ou du besoin de se donner bonne conscience facilement ?

### Performance ou Éco-responsabilité
 
Un code se doit d'être simple, efficace et pragmatique. Il faut aller à l'essentiel pour gagner en performance et économiser en ressources. Pourquoi ? Outre l'aspect maintenabilité, debug et j'en passe, cher au développeur, il y a la nécessité de répondre à un marché. Tous les utilisateurs de nos applications n'ont pas un PC dernier cri ou un smartphone de pointe. Or, notre principal objectif est de vendre nos applications au plus grand nombre d'utilisateurs possible. Si notre application est lourde et lente, les utilisateurs s'en détourneront au profit d'une application concurrente.
En tant que développeur, nous avons déjà le devoir d'optimiser nos produits afin de toucher le plus large public possible, et donc de vendre.

Il n'y a rien de *"Green"* à vouloir la performance et la simplicité d'un code pragmatique. Il n'y a rien de *"Vert"* à optimiser ses ressources et réduire les échanges avec le serveur. Il n'y a rien de *"responsable"*, à alléger ses bases de données pour accélérer les échanges. C'est du business. Juste... du... business.

Si l'illusion de l'écologie a poussé certains développeurs à s'améliorer en tant que tel, tant mieux. Mais c'est la recherche de la performance et de la perfection qui aurait dû les motiver dans leur quête de surpassement.

### L'écologie commerciale

Les années passant, les préoccupations écologiques sont de plus en plus ancrées dans les esprits. Accablés par une culture de la culpabilité, les consommateurs se tournent de plus en plus vers des marqueurs *"verts"* afin d'alléger leur conscience : les labels. Dès lors que la labellisation se repend dans un domaine, l'objet du label n'est plus qu'une marchandise. Aujourd'hui, l'écologie est un argument de vente. Ce n'est en rien un gage de qualité ou d'efficacité. Ce n'est pas non plus le gage d'une vie meilleure dans un monde décarboné. Ce n'est qu'une promesse illusoire et creuse... Pour vendre. La recherche de la qualité se perd au profit de la recherche de l'illusion de la qualité. Une fois encore, un code ne doit pas être *"vert"*, il doit être **"efficace"**. Et si par le plus grand des hasards, l'efficacité va de pair avec l'éco-responsabilité, tant mieux. Mais il ne faut pas oublier qu'au-delà du code, il y a le matériel et l'infrastructure qui va distribuer notre application. Et les solutions vertes ne seront pas forcément les plus efficaces.

### Des pratiques et des habitudes irresponsables

Avant de chercher à "rendre le code plus vert", peut-être faudrait-il s'interroger sur ses habitudes de développement. La logique écologique voudrait qu'on développe sur une machine optimisée pour la tâche, de préférences avec un OS vert *(il doit bien exister une ou deux distributions Linux pour ça...)*. Au vu des parts de marché des 3 principaux OS en 2023, c'est, dans les faits, fort peu probable.
La question du support mise à part, il faudrait aussi s'interroger sur les habitudes des développeurs. À une époque où l'IA se fait de plus en plus présente, il serait bon de se demander s'il n'est pas plus intéressant et écologique de passer 1 heure à rédiger un code à faible valeur ajoutée ou requêter une IA hébergée sur des milliers serveurs, consommant des centaines de milliers de MWh par jours et des milliards de litres d'eau par an.

## Conclusion

L'écologie telle qu'elle est abordée dans le ***"Green code"*** et, plus généralement, le ***"Green IT"***, est une idéologie. Sous couvert d'éthique, elle est détournée afin de devenir un outil de marketing. Et pourtant, les recommandations que véhicule le ***Green Code*** tiennent du bon sens du point de vue du développement. Oui, il faut un code simple. Oui, il faut un code léger. Oui, il faut réduire les dépendances. Oui, il faut minimiser le volume requêtes. Non, une base de données ne doit pas être complexe. Non, un code ne doit pas être complexe. Et ce n'est pas par soucis d'écologie, c'est par soucis d'efficacité. Parce qu'une machine est fainéante. Parce qu'en tant qu'interface avec la machine, nous devons, nous, développeurs, être comme elle : fainéants.
Il est vraiment regrettable que la Logique et la Raison ne soit plus entendue aujourd'hui et qu'il faille des idéaux creux pour pousser certains développeurs à agir comme des développeurs, avec efficacité et pragmatisme.

## Sources
- https://www.esi-business-school.com/ecole/developpementdurable/green-it-green-computing/
- https://scient.fr/article-green-it-lecologie-ethique-et-ecologique-de-linformatique/
- https://www.hellocarbo.com/blog/reduire/green-computing/
- https://www.vigicorp.fr/le-green-code-coder-de-facon-plus-responsable/
- https://www.linkedin.com/pulse/sustainability-through-green-coding-software-development-smartlogiq
- https://www.free-work.com/fr/tech-it/blog/actualites-informatiques/le-green-coding-vraie-tendance
- https://www.geo.fr/environnement/ia-peut-elle-consommer-autant-electricite-irlande-openai-chatgpt-google-moteur-recherches-consommation-217016