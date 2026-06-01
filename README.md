# <img src=".github/assets/logo.png" alt="class logo" class="logo" width="120"/> R2.02 - Développement d'applications avec IHM

### IUT d'Aix-Marseille - Département Informatique Aix-en-Provence

* **Ressource :** [Syllabus R2.02](https://github.com/IUTInfoAix-R202/syllabus) (compétences, calendrier, évaluations, ressources détaillées)

* **Équipe pédagogique :**

  * [Sébastien Nedjar](mailto:sebastien.nedjar@univ-amu.fr) - responsable du module
  * [Frédéric Flouvat](mailto:frederic.flouvat@univ-amu.fr)
  * [Sophie Nabitz](mailto:sophie.nabitz@univ-avignon.fr)
  * [Samir Chtioui](mailto:samir.chtioui@gmail.com)

* **Besoin d'aide ?**
    * Consulter et/ou créer des [issues](https://github.com/IUTInfoAix-R202/tp4/issues)
    * [Email](mailto:sebastien.nedjar@univ-amu.fr) pour toute question


## TP4 - MVVM et injection de dépendances

> 🎓 **Accepter le devoir TP4 sur GitHub Classroom** : 👉 **https://classroom.github.com/a/uFNkMVKE**
>
> Cela crée votre dépôt personnel `IUTInfoAix-R202-2026/tp4-votreLogin`. La marche à suivre (Classroom puis Codespace) est commune à tous les TP : voir le [TP1](https://github.com/IUTInfoAix-R202/tp1#mise-en-place).

> 📚 **Cours associé** : [CM4 - MVVM, persistance et synthèse](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html). Chaque exercice ci-dessous renvoie aux slides correspondants.

## Objectifs de la séance

### Ce que vous saurez faire à la fin de cette séance

Les exercices de ce TP sont organisés en progression. Cette progression suit la **taxonomie de Bloom**, un modèle pédagogique qui décrit les niveaux de maîtrise d'un savoir-faire du plus simple (comprendre un concept) au plus complexe (créer une application complète).

| Niveau Bloom | Exercices | Vous serez capable de... | Compétence BUT |
|---|---|---|---|
| **Comprendre** | 1-2 | expliquer le pattern **MVVM** : un *ViewModel* expose des propriétés observables et reste **testable sans interface graphique** (Message, Compteur) | AC11.04 |
| **Appliquer** | 3-4 | construire un ViewModel avec **validation et commandes**, puis remplacer le câblage manuel par l'**injection de dépendances** avec Guice (`@Inject`, `Module`, `controllerFactory`) | AC11.04, AC12.02 |
| **Analyser** | 5-6 | exposer une **collection observable** dans une `TableView` alimentée par un service injecté, et maîtriser les mécanismes avancés de Guice (**scopes**, **`@Named`**, **modules de test** avec mocks) | AC11.04, AC12.02 |
| **Créer** | 7 | assembler une architecture **MVVM + Guice complète** sur un écran réel de la SAÉ : la vérification d'une nuit d'enregistrement | AC11.02, AC11.04, AC12.02 |

### Pourquoi cette démarche

- **Pourquoi MVVM ?** Au TP3, le contrôleur FXML faisait tout : il connaissait la vue, contenait la logique d'affichage et appelait le métier. Quand l'écran grossit, ce *fat controller* devient impossible à tester (il faut monter une fenêtre JavaFX) et à réutiliser. MVVM interpose une couche, le **ViewModel**, qui porte l'état de l'écran sous forme de propriétés observables, **sans connaître JavaFX**. Conséquence directe : on teste la logique d'affichage avec un simple test JUnit, instantané. C'est ce que vous vérifierez dès l'exercice 1.

- **Pourquoi l'injection de dépendances (Guice) ?** Câbler `new ServiceX()` à la main dans chaque classe crée un **couplage fort** : impossible de remplacer une implémentation par une autre (ou par un mock de test) sans modifier le code. Guice centralise ces choix dans un **module** unique : c'est la documentation vivante de l'architecture, et la clef qui rend chaque couche testable indépendamment.

- **Pourquoi le TDD baby steps ?** Vous activez les tests **un par un** (en retirant `@Disabled`), vous les voyez rouges, puis vous écrivez le minimum pour les rendre verts. Cette discipline construit les automatismes attendus aux évaluations sur papier.

### Lien avec la SAÉ 2.01 (VigieChiro PR Companion)

La SAÉ 2.01 vous demande de réaliser le [*VigieChiro PR Companion*](https://iutinfoaix-s201.github.io/brief/), l'application qui aide les possesseurs de Passive Recorder à traiter leurs nuits de capture de chauves-souris. Aux TP1-3, vous avez construit des IHM (procédural, propriétés, FXML/MVC). Le TP4 vous fait franchir l'étape qui sépare un exercice jouet d'une **vraie application** : une architecture **testable et modulaire** (MVVM + injection de dépendances), exactement celle attendue dans la SAÉ.

| TP4 | Rôle dans la SAÉ |
|---|---|
| Ex 1-2 - Message, Compteur MVVM | les briques du pattern : ViewModel + propriétés + commandes |
| Ex 3-4 - Formulaire de connexion (validation, puis Guice) | gabarit d'un écran de saisie validé, câblé par injection |
| Ex 5 - Pokédex (`ObservableList` + `TableView`) | préfigure les **listes réactives** (sites, séquences) de l'application |
| Ex 6 - Guice avancé (scopes, `@Named`, mocks) | l'outillage qui rendra la SAÉ testable couche par couche |
| **Ex 7 - Vérifier une nuit d'enregistrement (capstone)** | le parcours [**P3 - Vérifier l'enregistrement**](https://iutinfoaix-s201.github.io/brief/Analyse%20et%20conception/Maquettes/M-Qualification/) en MVVM + Guice : c'est l'écran M-Qualification, déjà croisé au TP3, désormais **proprement architecturé** |

Au TP3 vous avez écrit l'écran de qualification en MVC. Au TP4 vous le réécrivez en MVVM + Guice et mesurez la différence : la logique migre dans un ViewModel testable, et le service de données est **injecté** (donc remplaçable par la vraie source de données du **TP5**). La boucle TP3 → TP4 → TP5 → SAÉ se referme.

---

> [!NOTE]
> **Mise en place, évaluation, commandes, workflow Git, assistance IA et dépannage : identiques aux TP précédents.** Pour ne pas dupliquer ce qui a déjà été présenté aux TP1 à TP3, reportez-vous au README du TP1 :
> [Mise en place (Classroom + Codespace)](https://github.com/IUTInfoAix-R202/tp1#mise-en-place) · [Comment vous êtes évalué·e](https://github.com/IUTInfoAix-R202/tp1#rendu-du-travail-et-évaluation) · [Commandes Maven](https://github.com/IUTInfoAix-R202/tp1#commandes-essentielles) · [Workflow Git par exercice](https://github.com/IUTInfoAix-R202/tp1#workflow-de-développement---un-cycle-par-exercice) · [Assistance IA (Copilot Chat)](https://github.com/IUTInfoAix-R202/tp1#assistance-ia) · [Dépannage](https://github.com/IUTInfoAix-R202/tp1#dépannage)

Le TP4 comporte **7 exercices principaux + 2 bonus**, à faire dans l'ordre. Chaque exercice vit dans son propre sous-paquet (code et tests en miroir).

---

> [!TIP]
> **Le réflexe MVVM de tout le TP** : la logique (calculs, validations, transformations d'affichage) vit dans le **ViewModel**, qui n'importe **jamais** de composant d'interface (`Label`, `Button`, `TableView`...). Le contrôleur de vue ne fait que **brancher les fils** (`bind`, `bindBidirectional`, appeler une commande). C'est ce qui rend la logique testable sans fenêtre.

---

## Vue d'ensemble : une architecture construite brique par brique

Tout le TP construit **une seule architecture**, celle que vous reconduirez en SAÉ : une application **en couches**, où chaque couche a une responsabilité unique et ne parle qu'à sa voisine.

<img alt="Architecture en couches du TP4 : Vue, ViewModel, Modèle/Service, assemblés par l'injection Guice" src=".github/assets/architecture-vue-d-ensemble.svg"/>

| Couche | Rôle | Règle d'or |
|---|---|---|
| **Modèle / Service** | porte les données et leur accès | ignore l'interface graphique |
| **ViewModel** | l'état de l'écran (propriétés observables) + les commandes | n'importe **jamais** un `Label`/`Button` → testable en JUnit |
| **Vue** (FXML + contrôleur) | affichage et saisie | le contrôleur ne fait que **brancher les fils** |
| **Injection** (Guice) | assemble les couches | un seul endroit décide des implémentations |

**La progression du TP suit cette architecture** : vous construisez d'abord le MVVM à la main (exercices 1-3), puis vous remplacez le câblage manuel par l'**injection de dépendances** (exercice 4), vous exposez des **collections réactives** (exercice 5) et maîtrisez l'injection avancée (exercice 6), avant de **tout assembler** sur un écran réel de la SAÉ (exercice 7). La boucle se referme au **TP5**, qui branchera une vraie base de données à la place du service.

Le **lanceur** (`./mvnw javafx:run`) liste les exercices ; chaque bouton ouvre la fenêtre correspondante.

<img alt="Le lanceur du TP4 : un bouton par exercice" src=".github/assets/apercu-lanceur.png" width="430"/>

---

## Exercice 1 - Hello MVVM (Message)

### 📖 MVVM : pourquoi interposer un ViewModel

Une interface graphique mélange spontanément trois préoccupations : les **données** (le modèle), leur **affichage** (les composants graphiques) et la **logique** qui relie les deux. Tout déposer dans le contrôleur FXML - le réflexe du TP3 - produit un *fat controller* : la logique d'affichage s'entremêle aux `Label` et aux `Button`, si bien que pour vérifier une règle aussi simple que « quand le champ change, l'aperçu suit », il faut démarrer une vraie fenêtre JavaFX.

MVVM (*Model-View-ViewModel*) tranche ce nœud en interposant une couche : le **ViewModel**. Il porte l'état de l'écran sous forme de **propriétés observables** et n'importe **jamais** un composant d'interface ; la vue ne fait plus que s'y **lier** (data binding). La logique d'affichage redevient alors une classe Java ordinaire, testée en quelques millisecondes par un simple test JUnit, sans fenêtre.

Le pattern a été formalisé chez Microsoft (WPF, 2005) précisément pour exploiter le data binding ; on le retrouve aujourd'hui, sous des noms voisins, dans la plupart des frameworks réactifs (Android Jetpack, Vue, Angular). C'est l'aboutissement du **pattern Observer** (vu au CM1) : ici, c'est tout l'état de l'écran qui devient observable.

**Objectif** : découvrir les trois couches de MVVM sur l'exemple le plus simple possible, et constater qu'on teste le ViewModel **sans aucune interface graphique**.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Hello MVVM (Message)](src/main/resources/assets/maquette_message.svg)

**Le rendu final** (votre objectif une fois l'exercice terminé, à comparer avec la maquette ci-dessus) :

<img alt="Résultat attendu - Exercice 1 : un champ de saisie et, en dessous, l'aperçu lié au texte" src=".github/assets/apercu-ex1-message.png" width="360"/>

**Ce que vous allez découvrir** :
- Le **Modèle** : un POJO Java ordinaire (`Message`) qui ne porte que des données, sans aucune référence à JavaFX.
- Le **ViewModel** : la couche qui expose l'état de l'écran sous forme de **propriétés observables** ([`StringProperty`](https://openjfx.io/javadoc/25/javafx.base/javafx/beans/property/StringProperty.html)) et n'importe **jamais** de composant d'interface (`Label`, `TextField`...). C'est précisément ce qui le rend testable par un simple test JUnit.
- Une propriété **dérivée** : `apercu` se recalcule tout seul à partir de `texte` grâce à la classe [`Bindings`](https://openjfx.io/javadoc/25/javafx.base/javafx/beans/binding/Bindings.html). Vous ne l'écrivez **jamais à la main**, vous la **liez**.
- Le **contrôleur de vue mince** : il ne contient aucune logique, il ne fait que « brancher les fils » entre le FXML et le ViewModel (`bind`, `bindBidirectional`).
- Le **bootstrap** : `MessageApp` assemble Modèle → ViewModel → Vue et installe le contrôleur via `setControllerFactory`. Retenez cette ligne : l'**exercice 4** la remplacera par Guice.

> 📚 **Cours** : [CM4 #19-20](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#19) - l'exemple **MessageView**, qui est exactement cet exercice ; et [CM4 #13-17](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#13) (le pattern MVVM, le ViewModel comme classe Java pure testable sans UI).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice1` :
- `Message.java` : le modèle (POJO `getTexte`/`setTexte`), **fourni complet**.
- `MessageViewModel.java` : le ViewModel, à **compléter dans le constructeur** (3 gestes balisés par `// TODO exercice 1`).
- `MessageController.java` : le contrôleur, à **compléter dans `initialize()`** (2 liaisons).
- `MessageView.fxml` : la vue, **fournie complète** (un `TextField fx:id="champTexte"` et un `Label fx:id="labelApercu"`).
- `MessageApp.java` : le bootstrap, **fourni complet** : lisez-le, il montre l'assemblage des trois couches.
- `MessageViewModelTest.java` : **3 tests JUnit purs** (le ViewModel, sans fenêtre) à activer un par un (retirez `@Disabled`).
- `MessageControllerTest.java` : **2 tests TestFX** (le câblage de la vue) à activer de même.

### <strong>📋 Rappel MVVM</strong> (les trois couches et leurs liaisons)

<img alt="MVVM : les trois couches Modèle, ViewModel, Vue et leurs liaisons (getXxx/setXxx, bind/bindBidirectional)" src=".github/assets/memo-mvvm-trois-couches.svg"/>

**La convention de propriété observable** (revient à chaque exercice du TP) :

```java
private final StringProperty texte = new SimpleStringProperty(); // le champ privé
public StringProperty texteProperty() { return texte; }          // l'accesseur (convention JavaBeans FX)
```

**Les trois verbes de liaison** :
- `a.bind(b)` : liaison **simple**, `a` suit `b` (sens unique) ; `a` n'est plus modifiable à la main.
- `a.bindBidirectional(b)` : liaison **deux sens**, toute modification de l'un se répercute sur l'autre.
- `Bindings.concat("texte : ", b)` : crée une valeur **dérivée** qui se recalcule quand `b` change.


**Travail à faire** : compléter le ViewModel puis le contrôleur. Cinq gestes.

*Dans `MessageViewModel` (constructeur) :*

1. **Initialiser** la propriété `texte` avec la valeur que porte déjà le modèle (`message.getTexte()`).

2. **Garder le modèle synchronisé** : faites en sorte que toute modification de `texte` soit recopiée dans le modèle. Indice : une propriété observable accepte un *écouteur de changement* (`addListener`).

3. **Dériver l'aperçu** : `apercu` doit toujours valoir le texte saisi précédé de la mention « Apercu : » (le format exact attendu est lisible dans le test). Indice : la classe `Bindings` sait concaténer une constante et une propriété observable.

*Dans `MessageController.initialize()` :*

4. Relier le champ de saisie (`champTexte`) et la `texteProperty()` du ViewModel par une liaison **bidirectionnelle** : ce que tape l'utilisateur va dans le ViewModel, et inversement.
5. Relier le label d'aperçu (`labelApercu`) à `apercuProperty()` par une liaison **simple** (sens unique) : le label se contente d'afficher ce que le ViewModel calcule.

**Progression des tests**, à activer un par un :

| Test | Ce qui est vérifié | Geste qui le fait passer |
|---|---|---|
| `au_demarrage_le_texte_reflete_la_valeur_du_modele` | `texte` est initialisé depuis le modèle | geste 1 |
| `modifier_la_propriete_met_a_jour_le_modele` | écrire dans `texte` met à jour le modèle | geste 2 |
| `l_apercu_derive_automatiquement_du_texte` | `apercu` vaut `"Apercu : " + texte` | geste 3 |
| `au_demarrage_l_apercu_reflete_le_texte_initial` | le label d'aperçu est bien relié au ViewModel | geste 5 |
| `saisir_dans_le_champ_met_a_jour_l_apercu` | taper dans le champ se propage jusqu'à l'aperçu | gestes 4 + 5 |

> [!NOTE]
> **Deux familles de tests, deux outils.** Les trois premiers (`MessageViewModelTest`) testent le ViewModel **sans fenêtre** : c'est instantané, et c'est là que vit l'essentiel de la logique MVVM. Les deux derniers (`MessageControllerTest`) sont des tests **TestFX** : ils ouvrent réellement la vue pour vérifier le seul rôle du contrôleur, **brancher les fils**. Chaque couche se teste avec l'outil adapté.

> [!TIP]
> Pour voir le résultat, `./mvnw javafx:run` puis ouvrez la fenêtre [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080 de l'onglet **PORTS**) et cliquez sur **Exercice 1 - Hello MVVM** dans le **Lanceur**. Tapez dans le champ : l'aperçu en gras se met à jour à chaque frappe, preuve que les trois couches sont reliées.

---

## Exercice 2 - Compteur MVVM (commandes)

### 📖 Le pattern Command : une action est une donnée

Cliquer sur un bouton, choisir un menu, frapper un raccourci : autant de façons de déclencher la **même** intention de l'utilisateur. Si chaque déclencheur appelle directement le code métier, on recopie la logique partout et on la **soude** à l'interface. Le pattern **Command** range chaque intention dans une **méthode publique sans paramètre** du ViewModel (`incrementerCommand`, `reinitialiserCommand`) que n'importe quel déclencheur peut appeler. Le bouton ne sait plus *quoi* faire, seulement *quelle commande* invoquer.

Deux bénéfices en découlent. La **testabilité** d'abord : une commande s'appelle directement dans un test, sans simuler de clic. La **séparation** ensuite : le ViewModel transforme aussi la donnée brute du modèle (un entier) en texte prêt à afficher (« Compteur a N ») - une *propriété dérivée* - de sorte que la vue n'a plus aucun calcul à faire, juste à se lier.

**Objectif** : exposer une propriété d'affichage **dérivée** et modéliser les actions de l'utilisateur avec le **pattern Command**.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Compteur MVVM](src/main/resources/assets/maquette_compteur.svg)

**Le rendu final** (votre objectif une fois l'exercice terminé, à comparer avec la maquette ci-dessus) :

<img alt="Résultat attendu - Exercice 2 : un compteur avec les boutons +, − et Réinitialiser" src=".github/assets/apercu-ex2-compteur.png" width="320"/>

**Ce que vous allez découvrir** :
- Une **propriété d'affichage dérivée** : le ViewModel transforme la donnée brute du modèle (un entier) en texte prêt à afficher (`"Compteur a N"`). La vue n'affiche jamais l'entier, elle affiche ce texte.
- Le **pattern Command** : chaque action de l'utilisateur (un clic) correspond à une **méthode publique sans paramètre** du ViewModel (`incrementerCommand`, `decrementerCommand`, `reinitialiserCommand`). Le contrôleur ne fait que les appeler.
- La **séparation des responsabilités** : le `Compteur` (déjà croisé aux TP1 et TP2) sait incrémenter/décrémenter ; le ViewModel orchestre et présente ; le contrôleur branche. Personne ne recalcule le texte à la main.
- Le **modèle a le droit d'exposer des propriétés observables** : `Compteur` porte une `IntegerProperty`. Une propriété observable appartient à `javafx.base`, ce n'est pas un composant d'interface.

> 📚 **Cours** : [CM4 #23](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#23) (les **commandes** : modéliser les actions) et [CM4 #24](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#24) (le contrôleur « léger » réduit à un câblage).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice2` :
- `Compteur.java` : le modèle (`IntegerProperty` + `incrementer`/`decrementer`/`reinitialiser`), **fourni complet**.
- `CompteurViewModel.java` : le ViewModel, à **compléter** (la liaison de `message` + les trois commandes).
- `CompteurController.java` : le contrôleur, à **compléter** dans `initialize()` (le bind du label) et dans les trois méthodes `surXxx` (relais vers les commandes).
- `CompteurView.fxml` : la vue, **fournie complète** (un `Label fx:id="labelCompteur"` et trois `Button` reliés par `onAction`).
- `CompteurApp.java` : le bootstrap, **fourni complet**.
- `CompteurViewModelTest.java` : **5 tests JUnit purs** (la logique, sans fenêtre).
- `CompteurControllerTest.java` : **4 tests TestFX** (le câblage de la vue : label + trois boutons).

### 📋 Le pattern Command

Une **commande** est une méthode publique sans paramètre du ViewModel qui représente une intention de l'utilisateur :

```java
public void incrementerCommand() {  // l'intention « incrémenter »
  compteur.incrementer();           // déléguée au modèle
}
```

Côté vue, le bouton FXML la déclenche via `onAction`, et le contrôleur ne fait que la relayer :

```xml
<Button text="+" onAction="#surIncrementer" />
```
```java
@FXML private void surIncrementer() { viewModel.incrementerCommand(); }
```

Bénéfice : l'action est **testable sans clic** (on appelle `incrementerCommand()` directement, cf. `CompteurViewModelTest`) et **réutilisable** (un bouton, un raccourci, un menu peuvent déclencher la même commande).

**Travail à faire** : compléter le ViewModel puis le contrôleur. Quatre gestes.

*Dans `CompteurViewModel` :*
1. **Lier `message`** pour qu'il affiche `"Compteur a N"` et se recalcule tout seul quand le compteur change. Indice : `Bindings.concat` avec la `valeurProperty()` du modèle.
2. **Compléter les trois commandes** : chacune **délègue** son action au modèle. Ne recalculez surtout pas le texte à la main, le binding du geste 1 s'en occupe.

*Dans `CompteurController` :*
3. **Lier** le texte de `labelCompteur` au `message` du ViewModel (liaison **simple**, sens unique).
4. **Relayer les clics** : chaque méthode `surXxx` appelle la commande correspondante du ViewModel.

**Progression des tests**, à activer un par un :

*Tests du ViewModel (JUnit, sans fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `au_demarrage_le_message_affiche_zero` | `message` vaut `"Compteur a 0"` | geste 1 |
| `incrementer_augmente_le_compteur_de_un` | `incrementerCommand` | gestes 1 + 2 |
| `plusieurs_increments_se_cumulent` | les incréments se cumulent | gestes 1 + 2 |
| `decrementer_diminue_le_compteur` | `decrementerCommand` | gestes 1 + 2 |
| `reinitialiser_remet_le_compteur_a_zero` | `reinitialiserCommand` | gestes 1 + 2 |

*Tests du contrôleur (TestFX, avec fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `au_demarrage_le_label_affiche_zero` | le label est relié au `message` | geste 3 |
| `cliquer_sur_plus_incremente_le_label` | le bouton `+` appelle la commande | gestes 3 + 4 |
| `cliquer_sur_moins_decremente_le_label` | le bouton `-` appelle la commande | gestes 3 + 4 |
| `cliquer_sur_reinitialiser_remet_a_zero` | le bouton `Reinitialiser` appelle la commande | gestes 3 + 4 |

> [!NOTE]
> **Deux familles de tests, deux outils** (comme à l'exercice 1) : `CompteurViewModelTest` teste la logique **sans fenêtre** (instantané), `CompteurControllerTest` ouvre la vue en **TestFX** pour vérifier le seul rôle du contrôleur : brancher le label et relayer les clics.

> [!TIP]
> `./mvnw javafx:run` puis [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080) → **Exercice 2 - Compteur MVVM**. Cliquez : **aucune ligne de votre code ne met à jour le label**. Vous changez un entier, le binding recalcule le texte, le label suit. Une seule source de vérité.

---

## Exercice 3 - Formulaire de connexion (validation + commande)

### 📖 Valider en se liant à l'état, pas en réagissant au clic

La validation naïve attend l'action : l'utilisateur remplit le formulaire, clique, et *alors* on vérifie. La validation **réactive** inverse la logique : une propriété booléenne (`validable`) se **recalcule à chaque frappe** à partir de l'état des champs, et l'interface s'y lie. Le bouton « Se connecter » se **désactive tout seul** tant que la saisie est incomplète. On ne signale plus l'erreur après coup : on l'**empêche**. C'est l'**affordance** (Don Norman) et l'heuristique de **prévention des erreurs** (Nielsen #5).

Reste le résultat de l'action. Une connexion peut échouer, mais une exception ne doit **jamais** remonter à l'utilisateur sous forme de trace technique. Le ViewModel **capture** l'issue et publie un message clair, en langage humain (« Identifiants incorrects… »), dans une propriété `statut` - heuristique #9, *aider à récupérer après une erreur*. Enfin, pour rester testable avec un faux service, le ViewModel dépend d'une **interface** (`ServiceAuth`), pas d'une implémentation : la programmation par interfaces, fondation de l'injection du prochain exercice.

**Objectif** : valider une saisie de façon **réactive** et gérer le résultat d'une action sans laisser remonter d'exception à l'interface.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Formulaire de connexion (validation réactive)](src/main/resources/assets/maquette_formulaire_connexion.svg)

**Le rendu final** (à gauche, champs vides : bouton désactivé et grisé ; à droite, après saisie de `marie` / `chiro2026` : bouton vert actif et statut de bienvenue) :

<img alt="Exercice 3 - état initial : formulaire vide, bouton « Se connecter » désactivé (grisé)" src=".github/assets/apercu-ex3-formulaire.png" width="300"/> <img alt="Exercice 3 - après saisie : champs remplis, bouton « Se connecter » vert actif, statut « Bienvenue marie ! »" src=".github/assets/apercu-ex3-formulaire-rempli.png" width="300"/>

**Ce que vous allez découvrir** :
- La **validation réactive** : une propriété booléenne (`validable`) se recalcule à chaque frappe. Le bouton se **désactive tout seul** tant que la saisie est incomplète : on **empêche l'erreur** au lieu de la signaler après coup (**affordance**, heuristique de Nielsen #5).
- Le **pattern Command avec gestion d'erreur** : `connecterCommand` ne laisse **jamais** remonter d'exception ; il publie le résultat dans `statut`, en **langage humain** (Nielsen #9).
- La **dépendance à une interface** : le ViewModel dépend de `ServiceAuth` (interface), pas d'une implémentation. C'est ce découplage qui le rend testable avec un **faux service** (un simple lambda dans le test) et qui prépare l'**injection** de l'exercice 4.
- Un nouveau contrôle : le `PasswordField` (un `TextField` qui masque la saisie).

> 📚 **Cours** : [CM4 #25](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#25) (validation côté ViewModel) et [CM4 #26](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#26) (gérer les erreurs dans une commande) ; côté ergonomie [CM4 #67](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#67) (Nielsen #5, prévention des erreurs) et [CM4 #70](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#70) (Nielsen #9). La brique des bindings booléens vient de [CM2 #53-56](https://iutinfoaix-r202.github.io/cours/cm2-donnees-et-liaison.html#53).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice3` :
- `ServiceAuth.java` : l'**interface** d'authentification (`boolean connecter(id, mdp)`), **fournie**.
- `ServiceAuthSimple.java` : une implémentation de démo (compte `marie` / `chiro2026`), **fournie complète**. Une vraie application interrogerait une base (cf. TP5).
- `FormulaireConnexionViewModel.java` : le ViewModel, à **compléter** (la règle `validable` + `connecterCommand`).
- `FormulaireConnexionController.java` : le contrôleur, à **compléter** (`initialize()` + `surValider`).
- `FormulaireConnexionView.fxml` : la vue, **fournie complète** (champs, bouton et statut avec leurs `fx:id`).
- `FormulaireConnexionApp.java` : le bootstrap, **fourni complet** : il choisit `ServiceAuthSimple` et la passe au ViewModel (ce que Guice fera à l'exercice 4).
- `FormulaireConnexionViewModelTest.java` : **5 tests JUnit purs** (la logique, sans fenêtre).
- `FormulaireConnexionControllerTest.java` : **3 tests TestFX** (le câblage de la vue).

### 📋 Exprimer une règle de validation

Une règle de validation s'écrit comme une **expression booléenne observable**, pas comme un `if` recalculé à la main :

```java
BooleanBinding nonVide = champ.textProperty().isNotEmpty(); // se réévalue à chaque frappe
bouton.disableProperty().bind(nonVide.not());               // bouton actif seulement si non vide
```

Les fabriques utiles sur une `StringProperty` : `isNotEmpty()`, `isEmpty()`. On combine plusieurs conditions avec `.and(...)`, `.or(...)`, `.not()`.

**Travail à faire** : compléter le ViewModel puis le contrôleur. Cinq gestes.

*Dans `FormulaireConnexionViewModel` :*
1. **Rendre le formulaire validable** uniquement quand l'identifiant **et** le mot de passe sont non vides. Indice : voir le rappel ci-dessus, et combiner les deux conditions.
2. **Implémenter `connecterCommand`** : publier un statut « en cours », demander au `serviceAuth` de connecter, puis publier un message **clair** selon le succès (`"Bienvenue <id> !"`) ou l'échec. La commande ne doit **jamais** laisser remonter d'exception.

*Dans `FormulaireConnexionController` :*
3. **Lier les deux champs** au ViewModel en **bidirectionnel** (identifiant et mot de passe).
4. **Lier le statut** (sens unique) et **désactiver le bouton** tant que le formulaire n'est pas validable. Indice : `disableProperty()` suit l'**inverse** de `validable`.
5. **Relayer le clic** : `surValider` appelle `connecterCommand`.

**Progression des tests**, à activer un par un :

*Tests du ViewModel (JUnit, sans fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `le_formulaire_n_est_pas_validable_au_demarrage` | `validable` est faux si les champs sont vides | geste 1 |
| `le_formulaire_devient_validable_quand_les_deux_champs_sont_remplis` | `validable` passe à vrai | geste 1 |
| `une_connexion_reussie_affiche_un_message_de_bienvenue` | message de succès | geste 2 |
| `une_connexion_refusee_affiche_un_message_d_erreur` | message d'échec clair (Nielsen #9) | geste 2 |
| `le_service_simple_reconnait_le_compte_de_demonstration` | le service de démo (fourni) reconnaît `marie`/`chiro2026` | *(rien à faire)* |

*Tests du contrôleur (TestFX, avec fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `au_demarrage_le_bouton_est_desactive` | le bouton est désactivé au départ | geste 4 |
| `remplir_les_deux_champs_active_le_bouton` | les champs sont reliés et activent le bouton | gestes 3 + 4 |
| `se_connecter_affiche_le_message_de_bienvenue` | le clic relaie la commande, le statut s'affiche | gestes 3 + 4 + 5 |

> [!NOTE]
> **Deux familles de tests, deux outils** : `FormulaireConnexionViewModelTest` teste la validation et la connexion **sans fenêtre** (avec un faux service en lambda) ; `FormulaireConnexionControllerTest` ouvre la vue en **TestFX** pour vérifier le câblage (champs, bouton, statut).

> [!TIP]
> `./mvnw javafx:run` puis [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080) → **Exercice 3 - Formulaire MVVM**. Tapez dans les champs : le bouton « Se connecter » **s'active tout seul** dès que les deux sont remplis. Connectez-vous avec `marie` / `chiro2026`.

---

## Exercice 4 - L'injection de dépendances avec Guice

### 📖 Inversion de contrôle : qui assemble les objets ?

Jusqu'ici, chaque classe créait elle-même ses dépendances : `new ServiceAuthSimple()` au cœur du code. C'est simple, mais cela **soude** les classes entre elles : impossible de remplacer une implémentation (par une autre, ou par un mock de test) sans rouvrir le code. Le couplage est *en dur*.

L'**inversion de contrôle** (IoC) renverse la responsabilité : une classe **déclare** ce dont elle a besoin (`@Inject`) au lieu de le **fabriquer**. Un tiers - le **conteneur d'injection**, ici **Guice** - construit le graphe d'objets et livre à chacun ses dépendances. Les choix d'implémentation se concentrent en un seul endroit, le **module** (la *composition root*) : c'est la documentation vivante de l'architecture, et la clef qui rend chaque couche remplaçable et testable indépendamment. Le comportement de l'exercice 3 ne change pas ; seul son **câblage** change.

**Objectif** : remplacer le câblage manuel de l'exercice 3 par l'**injection de dépendances**. Vous mesurerez ce que Guice automatise.

### Maquette à reproduire

Voici l'interface que vous devez construire (le même écran que l'ex3, mais c'est Guice qui assemble la chaîne Contrôleur → ViewModel → ServiceAuth) :

![Maquette Connexion assemblée par Guice](src/main/resources/assets/maquette_connexion_guice.svg)

**Le rendu final** (identique à l'ex3 — à gauche vide, bouton désactivé ; à droite après saisie de `marie` / `chiro2026`, bouton actif et statut de bienvenue — mais ici tout est câblé par injection) :

<img alt="Exercice 4 - état initial : formulaire vide, bouton « Se connecter » désactivé" src=".github/assets/apercu-ex4-connexion.png" width="300"/> <img alt="Exercice 4 - après saisie : champs remplis, bouton vert actif, statut « Bienvenue marie ! » — assemblé par Guice" src=".github/assets/apercu-ex4-connexion-rempli.png" width="300"/>

**Ce que vous allez découvrir** :
- **`@Inject`** : posée sur un constructeur (ou un champ), elle dit à Guice « fournis-moi cette dépendance ».
- **Le module** (`AppModule extends AbstractModule`) : l'unique endroit qui choisit l'implémentation concrète, via `bind(ServiceAuth.class).to(ServiceAuthSimple.class)`. C'est la **documentation vivante** de l'architecture : on lit tout le câblage en un fichier.
- **L'injecteur + `controllerFactory`** : `loader.setControllerFactory(injector::getInstance)` fait que **tous** les contrôleurs FXML sont construits par Guice et reçoivent leurs `@Inject`.
- **Le ViewModel reste ignorant de Guice** : à part l'annotation `@Inject`, il ne crée aucun service et ne connaît aucune implémentation. Il reste donc testable directement avec un faux service (comme à l'ex3).

> 📚 **Cours** : toute la Partie 3 du CM4 - [CM4 #32](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#32) (le problème du `new` partout), [CM4 #35-37](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#35) (Guice, les trois styles d'injection, le module) et [CM4 #39](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#39) (intégration `FXMLLoader` / `controllerFactory`).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice4` :
- `AppModule.java` : le module Guice, à **compléter** (le `bind`).
- `ConnexionViewModel.java` : le ViewModel (logique **reprise de l'ex3**, déjà écrite), à **compléter** d'une seule annotation `@Inject`.
- `ConnexionController.java` : le contrôleur, **fourni complet** (le câblage est celui de l'ex3 ; notez le `@Inject` sur le champ ViewModel).
- `ConnexionView.fxml` : la vue, **fournie complète**.
- `ConnexionApp.java` : le bootstrap, à **compléter** (créer l'injecteur, brancher le `controllerFactory`, charger, afficher).
- Le service `ServiceAuth` / `ServiceAuthSimple` est **réutilisé de l'exercice 3**.
- `AppModuleTest.java` : **2 tests JUnit purs** (le câblage Guice, sans fenêtre).
- `ConnexionControllerTest.java` : **4 tests TestFX** (l'application réelle démarre et se connecte : preuve que la chaîne est câblée de bout en bout).

### 📋 Les trois pièces de Guice

<img alt="Les trois pièces de Guice - le module (bind), l'injecteur, et le controllerFactory qui assemble la chaîne Contrôleur -> ViewModel -> Service" src=".github/assets/memo-guice-trois-pieces.svg"/>

- **`@Inject`** = « j'ai besoin de ça » (sur un constructeur ou un champ).
- **`bind(A.class).to(B.class)`** = « quand on demande un `A`, donne un `B` » (uniquement pour relier une **interface** à une **implémentation**).
- **`injector::getInstance`** comme `controllerFactory` = Guice devient la fabrique de tous les contrôleurs FXML.

**Travail à faire** : trois gestes, un par pièce.

1. **Dans `AppModule.configure()`** : déclarer que `ServiceAuth` est fourni par `ServiceAuthSimple`. Indice : `bind(...).to(...)`.
2. **Dans `ConnexionViewModel`** : annoter le **constructeur** avec `@Inject` pour que Guice sache l'utiliser.
3. **Dans `ConnexionApp`** : créer l'injecteur à partir du module, puis dire au `FXMLLoader` d'utiliser l'injecteur comme **fabrique de contrôleurs**, charger la vue et l'afficher. Indice : `setControllerFactory` attend une fonction `Class -> Object` ; l'injecteur en possède déjà une.

**Progression des tests**, à activer un par un :

*Tests du module (JUnit, sans fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `le_module_lie_serviceauth_a_son_implementation_simple` | le `bind` fonctionne | geste 1 |
| `guice_construit_le_viewmodel_avec_son_service_injecte` | Guice construit le ViewModel (donc `@Inject` posé) et le service est injecté | gestes 1 + 2 |

*Tests de bout en bout (TestFX, avec fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `la_fenetre_s_ouvre_avec_le_formulaire` | l'application réelle démarre et affiche la vue | geste 3 |
| `au_demarrage_le_bouton_est_desactive` | le ViewModel injecté pilote le bouton | gestes 1 + 2 + 3 |
| `se_connecter_via_guice_affiche_la_bienvenue` | connexion réussie de bout en bout | gestes 1 + 2 + 3 |
| `un_mauvais_mot_de_passe_affiche_l_erreur` | le **vrai** `ServiceAuthSimple` est injecté (l'échec est refusé) | gestes 1 + 2 + 3 |

> [!NOTE]
> **Deux familles de tests, deux outils** : `AppModuleTest` vérifie le câblage Guice **sans fenêtre** (instantané) ; `ConnexionControllerTest` lance l'**application réelle** en TestFX pour prouver que votre bootstrap (geste 3) assemble bien la chaîne complète.

> Comparez `ConnexionApp` (ex4) et `FormulaireConnexionApp` (ex3) : plus aucun `new ServiceAuthSimple()` ni `new ...ViewModel(...)` à la main. Le câblage vit désormais dans le module.

> [!TIP]
> `./mvnw javafx:run` puis [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080) → **Exercice 4 - Connexion avec Guice**. La fenêtre est identique à celle de l'ex3, mais vous n'avez écrit **aucun** câblage de dépendances : Guice l'a fait.

---

## Exercice 5 - Pokédex (`ObservableList` + `TableView`)

### 📖 Des données qui préviennent quand elles changent

Une `StringProperty` est une chaîne *qui prévient* ses observateurs quand sa valeur change. Une **`ObservableList`** étend cette idée à une **collection** : elle notifie tout ajout, retrait ou remplacement. Branchée à une `TableView`, elle rend l'affichage **réactif** : on ne redessine jamais le tableau à la main (`refresh()`), on modifie la **liste** et la vue suit. C'est le **pattern Observer** (CM1) appliqué non plus à une valeur isolée, mais à un ensemble.

C'est exactement le modèle dont la SAÉ a besoin pour ses listes vivantes (sites, séquences). Et comme la source de données est **injectée** (un `PokemonService` ici, un DAO au TP5), la même vue réactive se branchera demain sur une vraie base sans toucher au ViewModel.

**Objectif** : exposer une **collection observable** dans une `TableView` qui se met à jour toute seule, et l'**enrichir** en demandant à un service (injecté par Guice) de retrouver un Pokémon par son nom.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Pokédex (ObservableList + TableView)](src/main/resources/assets/maquette_pokedex.svg)

**Le rendu final** (votre objectif une fois l'exercice terminé, à comparer avec la maquette ci-dessus) :

<img alt="Résultat attendu - Exercice 5 : une TableView de Pokémon, un résumé « 6 Pokémon », et un formulaire « Ajouter par son nom » avec « Dracaufeu » saisi et le bouton Ajouter actif (vert)" src=".github/assets/apercu-ex5-pokedex.png" width="440"/>

**Ce que vous allez découvrir** :
- L'`ObservableList` : à `List` ce qu'une `StringProperty` est à `String`. Une `TableView` abonnée à une `ObservableList` se rafraîchit **toute seule** à chaque ajout/retrait, **sans aucun `refresh()`**.
- La **cell value factory** : on explique à chaque colonne quelle donnée du modèle afficher.
- Une **propriété dérivée d'une collection** : le résumé est lié à la **taille** de la liste (`Bindings.size`), donc il se recalcule à chaque ajout.
- Le **service comme source de données** : il ne fournit pas qu'une liste de départ, il **répond à une recherche** (`chercherParNom`) - exactement le rôle d'un DAO au TP5.
- L'**injection sans module** : `PokemonService` et `PokemonViewModel` sont des classes **concrètes** ; Guice sait les construire seul. On ne déclare un binding que pour relier une **interface** à une implémentation (ex4).

> 📚 **Cours** : [CM4 #21](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#21) (`ObservableList` et data binding) et [CM4 #35-36](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#35) (le service de données injecté par Guice).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice5` :
- `Pokemon.java` : le modèle (`record` à trois champs `numero`/`nom`/`type`), **fourni complet**.
- `PokemonService.java` : le « Pokédex national » (**les 151 Pokémon** de la 1ʳᵉ génération), **fourni complet**. `tousLesPokemons()` renvoie un petit ensemble de départ ; `chercherParNom(...)` retrouve n'importe quel Pokémon par son nom (insensible à la casse et aux accents).
- `PokemonViewModel.java` : le ViewModel, à **compléter** (remplir la liste, lier le résumé, la commande `ajouter`).
- `PokemonController.java` : le contrôleur, à **compléter** dans `initialize()` (colonnes, abonnement, résumé, formulaire) et `surAjouter`.
- `PokemonView.fxml` : la vue, **fournie complète** (`TableView` + 3 colonnes + champ de recherche + bouton + statut, avec leurs `fx:id`).
- `PokemonApp.java` : le bootstrap, **fourni complet** (injecteur Guice **sans module**).
- `PokemonViewModelTest.java` : **6 tests JUnit purs** (la logique, sans fenêtre).
- `PokemonControllerTest.java` : **7 tests TestFX** (le câblage de la table et du formulaire).

### 📋 ObservableList et TableView

```java
ObservableList<X> liste = FXCollections.observableArrayList();
table.setItems(liste);   // abonnement : tout ajout/retrait dans `liste` se reflète dans la table

// chaque colonne dit quelle donnée afficher (cell value factory) :
colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().nom()));

// un résumé dérivé de la TAILLE de la liste :
resume.bind(Bindings.size(liste).asString().concat(" éléments"));
```

Le point clef : une fois `setItems` posé, **vous ne touchez plus jamais à la table**. Vous modifiez la **liste**, la table suit.

Et pour **ajouter** un élément à partir d'une recherche, on demande au service (qui renvoie un `Optional`) puis on agit selon le résultat :

```java
service.chercherParNom(nom).ifPresentOrElse(
    p -> liste.add(p),                        // trouvé -> on ajoute (la table suit)
    () -> statut.set("introuvable"));         // absent -> on informe l'utilisateur
```

**Travail à faire** : compléter le ViewModel puis le contrôleur. Sept gestes.

*Dans `PokemonViewModel` :*
1. **Remplir la liste observable** depuis le service. Indice : une `ObservableList` sait tout remplacer d'un coup (`setAll`).
2. **Lier `resume`** au nombre d'éléments de la liste (ex : `"6 Pokemon"`), de sorte qu'il se mette à jour à chaque ajout. Indice : `Bindings.size(...)`.
3. **Compléter `ajouter`** : demander au service le Pokémon nommé `recherche`, l'ajouter s'il existe (et n'est pas déjà présent), sinon publier un statut. Indice : `Optional.ifPresentOrElse(...)`.

*Dans `PokemonController.initialize()` :*
4. **Configurer chaque colonne** : dire quelle donnée du `Pokemon` afficher (cell value factory). `Pokemon` est un `record` : ses accesseurs sont `numero()`, `nom()`, `type()`.
5. **Abonner la TableView** à la liste observable du ViewModel (`setItems`).
6. **Lier le label résumé** au `resume` du ViewModel.
7. **Câbler le formulaire** : champ de recherche en **bidirectionnel** (`rechercheProperty`), label de statut en **sens unique**, et bouton **Ajouter désactivé** tant que la recherche est vide. Puis, dans `surAjouter`, relayer vers la commande `ajouter`.

**Progression des tests**, à activer un par un :

*Tests du ViewModel (JUnit, sans fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `au_demarrage_la_liste_contient_les_pokemon_de_depart` | la liste de départ est remplie | geste 1 |
| `le_resume_reflete_le_nombre_de_pokemon` | `resume` vaut `"6 Pokemon"` | geste 2 |
| `ajouter_par_nom_ajoute_le_pokemon_trouve_et_met_a_jour_le_resume` | l'ajout par nom + le résumé réactif | gestes 2 + 3 |
| `ajouter_un_nom_inconnu_laisse_la_liste_intacte_et_affiche_un_statut` | nom inconnu : rien ajouté, statut publié | geste 3 |
| `ajouter_un_pokemon_deja_present_ne_le_duplique_pas` | pas de doublon | geste 3 |
| `la_recherche_ignore_la_casse_et_les_accents` | « evoli » retrouve « Évoli » | geste 3 |

*Tests du contrôleur (TestFX, avec fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `au_demarrage_la_table_affiche_les_pokemon_de_depart` | la table est abonnée à la liste | geste 5 |
| `les_colonnes_affichent_les_donnees_du_premier_pokemon` | les cell value factories | geste 4 |
| `le_label_resume_affiche_le_compte` | le label est lié au résumé | geste 6 |
| `le_bouton_ajouter_est_desactive_sans_saisie` | le bouton est désactivé au départ | geste 7 |
| `remplir_le_champ_active_le_bouton` | le bouton s'active dès qu'on saisit | geste 7 |
| `ajouter_un_pokemon_par_son_nom_ajoute_une_ligne` | saisir + Ajouter fait grandir la table | gestes 5 + 7 + 3 |
| `un_nom_inconnu_affiche_un_statut_sans_ajouter` | un nom inconnu affiche le statut | gestes 7 + 3 |

> [!NOTE]
> **Deux familles de tests, deux outils** : `PokemonViewModelTest` teste la liste et le résumé **sans fenêtre** ; `PokemonControllerTest` ouvre la vue en **TestFX** pour vérifier l'abonnement de la table, les colonnes et le label.

> Remarquez `PokemonApp` : on crée l'injecteur **sans module** (`Guice.createInjector()`). Pour des classes **concrètes** (sans interface à choisir), Guice sait construire seul ; on ne déclare un binding que pour relier une interface à une implémentation.

> [!TIP]
> `./mvnw javafx:run` puis [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080) → **Exercice 5 - Pokedex (ObservableList)**. Tapez un nom (ex. **Dracaufeu**) et cliquez sur **Ajouter** : la ligne apparaît et le résumé s'incrémente. Vous n'avez écrit **aucun** `table.refresh()` : la table est abonnée à la liste.

---

## Exercice 6 - Guice avancé : scopes, `@Named`, modules de test

### 📖 Portée des objets et résolution des ambiguïtés

Déclarer *quelle* implémentation injecter (exercice 4) ne suffit pas toujours. Deux questions surgissent dès qu'une application grossit. **Combien d'instances ?** Par défaut, Guice fabrique un nouvel objet à chaque injection ; or certains services doivent être **uniques et partagés** par toute l'application (un journal, un cache) - c'est la notion de **portée** (*scope*), dont le `@Singleton`. **Laquelle, s'il y en a plusieurs ?** Si une même interface a deux implémentations valides, le conteneur ne peut pas deviner : on lève l'ambiguïté en les **nommant** (`@Named`).

Ces mécanismes font de l'injection un véritable outil d'architecture, pas un `new` déguisé. Et ils ouvrent la **testabilité avancée** : avec un module de test, on **remplace** une dépendance par une doublure (un *mock* Mockito) le temps d'un test, sans toucher au code applicatif - ce qui rendra la SAÉ vérifiable couche par couche.

**Objectif** : maîtriser trois mécanismes qui font de Guice un véritable outil d'architecture. **Cet exercice n'a pas d'interface graphique** : il se valide uniquement par ses tests (`./mvnw test`).

**Ce que vous allez découvrir** :
- Le **scope `@Singleton`** : `JournalActivite` est partagé par toute l'application (une **seule** instance). Sans `@Singleton`, Guice recrée une instance à chaque injection, et les événements seraient éparpillés dans des journaux distincts.
- **`@Named`** : quand une même interface (`Notifieur`) a **plusieurs** implémentations, un **nom** choisit laquelle injecter. On déclare `bind(...).annotatedWith(Names.named("console"))...`, et un composant demande `@Named("console")`.
- Le **module de test + mock** : avec `Modules.override`, on remplace une dépendance par un **mock Mockito** le temps d'un test, **sans toucher au code applicatif**. C'est l'outillage qui rendra la SAÉ testable couche par couche.

> 📚 **Cours** : [CM4 #40](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#40) (les **scopes** Guice, dont `@Singleton`), [CM4 #42](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#42) (`@Named` : résoudre les ambiguïtés) et [CM4 #38](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#38) (un module de test qui injecte des **mocks**).

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice6` :
- `Notifieur.java` : l'interface (`void notifier(String)`), **fournie**.
- `NotifieurConsole.java` / `NotifieurSilencieux.java` : deux implémentations interchangeables (l'une affiche, l'autre ne fait rien), **fournies**.
- `JournalActivite.java` : le journal partagé, annoté `@Singleton`, **fourni complet**.
- `ServiceSurveillance.java` : `@Inject` d'un `@Named("console") Notifieur` + du journal, **fourni complet**.
- `Exercice6Module.java` : le module, à **compléter** (les deux liaisons nommées).
- `Exercice6ModuleTest.java` : **5 tests JUnit purs** (singleton, choix par nom, construction via le module, partage du journal, mock).

### 📋 Trois mécanismes de Guice

```java
// 1. Scope : une seule instance, partagée par toute l'application
@Singleton
public class Journal { ... }

// 2. @Named : choisir une implémentation parmi plusieurs, par son nom
bind(Service.class).annotatedWith(Names.named("a")).to(ServiceA.class);
public MonClient(@Named("a") Service s) { ... }   // reçoit ServiceA

// 3. Module de test : remplacer une dépendance par un mock, le temps d'un test
Injector injector = Guice.createInjector(
    Modules.override(new MonModule()).with(new AbstractModule() {
      @Override protected void configure() {
        bind(Service.class).toInstance(monMock);   // sans toucher au code applicatif
      }
    }));
```

**Travail à faire** : un seul geste, dans `Exercice6Module.configure()`.
1. **Lier chaque implémentation de `Notifieur` à un nom** : `"console"` vers `NotifieurConsole`, `"silencieux"` vers `NotifieurSilencieux`. Indice : `bind(...).annotatedWith(Names.named(...)).to(...)`.

**Progression des tests** (tous JUnit, sans fenêtre), à activer un par un :

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `le_journal_est_un_singleton_partage` | `@Singleton` (fourni) : une seule instance partagée | *(rien à faire)* |
| `le_nom_choisit_l_implementation_du_notifieur` | `"console"` → `NotifieurConsole`, `"silencieux"` → `NotifieurSilencieux` | geste 1 |
| `le_service_surveillance_se_construit_avec_le_notifieur_nomme` | sans le bind `"console"`, Guice ne peut pas construire le service | geste 1 |
| `le_journal_singleton_cumule_les_evenements_de_plusieurs_services` | deux services partagent le même journal singleton | geste 1 |
| `un_module_de_test_peut_injecter_un_mock` | `Modules.override` remplace le notifieur par un mock | *(démonstration)* |

> [!NOTE]
> Cet exercice **n'a pas de bouton dans le lanceur** : il n'y a pas de fenêtre à montrer. Vous le validez entièrement avec `./mvnw test`. Les tests sont aussi une **démonstration** : lisez-les, ils montrent comment on exerce Guice (singleton, `@Named`, mock) sans jamais lancer d'application.

---

## Exercice 7 - Vérifier une nuit d'enregistrement (pierre angulaire SAÉ)

### 📖 Une architecture en couches, assemblée par l'injection

Ce capstone **réunit tout le TP**. Une application robuste s'organise en **couches** empilées, chacune ne parlant qu'à sa voisine : la **Vue** (FXML + contrôleur) affiche et capte les gestes ; le **ViewModel** tient l'état de l'écran sous forme de propriétés observables et expose des commandes ; le **service** (ici `ServiceNuits`, demain un DAO du TP5) fournit les données. Chaque couche **ignore** les détails de celle d'en dessous : le ViewModel ne sait pas d'où viennent les nuits, la vue ne sait pas qu'il y a un service.

Reste à **assembler** ces couches sans les souder : c'est le rôle de **Guice**, qui injecte le service dans le ViewModel et le ViewModel dans le contrôleur. Le bénéfice oriente toute la suite : remplacer `ServiceNuitsDemo` par la vraie source de données du **TP5** ne demandera **aucune** modification du ViewModel ni de la vue. C'est l'architecture exacte que vous reconduirez en SAÉ.

**Objectif** : assembler **tout** le TP (MVVM + commandes + `ObservableList` + Guice) sur un écran réel de la SAÉ. C'est le parcours **P3** du *VigieChiro PR Companion* (maquette M-Qualification), déjà rencontré au TP3 mais désormais proprement architecturé.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Qualification (capstone SAÉ)](src/main/resources/assets/maquette_qualification.svg)

**Le rendu final** (une séquence est sélectionnée : le panneau de droite se remplit et le bouton « Écouter » devient actif ; en bas, le composant `AudioView` affiche sonogramme et spectrogramme) :

<img alt="Résultat attendu - Exercice 7 : table des séquences à gauche (1re ligne sélectionnée), panneau sélection / commentaire / verdict à droite, sonogramme + spectrogramme en bas" src=".github/assets/apercu-ex7-qualification.png" width="720"/>

**Le scénario** : l'application charge une nuit de 10 séquences d'écoute (via un `ServiceNuits` injecté). L'utilisateur sélectionne une séquence dans le tableau, l'écoute (son statut passe à « Écoutée »), saisit un commentaire et enregistre un **verdict global** (`OK` / `Douteux` / `À jeter`).

**Ce que vous allez découvrir** (la synthèse de tout le TP) :
- le `ServiceNuits` est **injecté par Guice** (`NuitModule` relie l'interface à `ServiceNuitsDemo`) : demain, le **TP5** fournira une implémentation lisant les vraies données, **sans toucher au ViewModel** ;
- la liste des séquences est une **`ObservableList`** affichée dans une `TableView` ;
- les libellés (description de la sélection, libellé du verdict) sont des **propriétés dérivées** calculées par un binding **sur mesure** (`Bindings.createStringBinding`) ;
- « Écouter » et « Enregistrer le verdict » sont des **commandes** ;
- le bouton « Écouter » est **désactivé** tant qu'aucune séquence n'est sélectionnée (affordance) ;
- nouveauté du câblage : la **sélection** de la `TableView` est **relayée** au ViewModel par un `bind`.
- un **vrai composant réutilisé de la SAÉ** : `AudioView` affiche le **sonogramme** et le **spectrogramme** de la séquence sélectionnée (il **recharge** le fichier à chaque ligne), et « Écouter » le met en **lecture**. Pour vous, c'est une boîte noire : `new AudioView()` puis `setAudioFile(...)`.

> 📚 **Cours** : la synthèse [CM4 #29](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#29) (bilan MVVM) et [CM4 #39](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#39) (Guice + FXML) ; ce capstone mobilise toutes les Parties 2 et 3 du CM4.

**Le matériel fourni** dans le paquet `fr.univ_amu.iut.exercice7` :
- `Sequence.java`, `NuitVerification.java` : les modèles (séquence d'écoute, nuit), **fournis complets** (propriétés observables, réutilisés du TP3).
- `ServiceNuits.java` : l'**interface** de chargement (`NuitVerification chargerNuit()`), **fournie**.
- `ServiceNuitsDemo.java` : une implémentation de démo (10 séquences entre 20h et 6h), **fournie complète**.
- `NuitModule.java` : le module Guice (`bind(ServiceNuits).to(ServiceNuitsDemo)`), **fourni complet**.
- `QualificationViewModel.java` : le ViewModel, à **compléter** (2 libellés dérivés + 2 commandes).
- `QualificationController.java` : le contrôleur, à **compléter** dans `initialize()` (le câblage complet ; `surEcouter`/`surEnregistrerVerdict` sont fournis).
- `QualificationView.fxml` : la vue, **fournie complète** (TableView 4 colonnes + sélection + commentaire + ChoiceBox de verdict).
- `Qualification.java` : le bootstrap Guice, **fourni complet**.
- **Composant `AudioView`** (sonogramme / spectrogramme) : brique **réutilisée de la SAÉ** ; les enregistrements de démonstration `src/main/resources/audio/seq-*.wav` sont **fournis**, comme son intégration dans `QualificationController`. → **présenté en détail juste après.**
- `QualificationViewModelTest.java` : **8 tests JUnit purs** (la logique).
- `QualificationControllerTest.java` : **6 tests TestFX** (le câblage de la vue).

### 🔊 Le composant `AudioView` (fourni — une boîte noire réutilisable)

Le panneau du bas de l'écran (forme d'onde + spectrogramme) **n'est pas à coder** : c'est un **composant JavaFX prêt à l'emploi**, développé pour la **SAÉ 2.01** et réutilisé ici tel quel. Il **hérite de `BorderPane`**, donc il se manipule comme n'importe quel contrôle : on le pose dans le FXML et on le pilote depuis le contrôleur, exactement comme un `Button` ou une `TableView`.

- **D'où il vient** : une dépendance **JitPack** déjà déclarée dans le `pom.xml` (`com.github.IUTInfoAix-S201:audio-view:v1.3.0`), dans le paquet `fr.nedjar.vigiechiro.audio`, déjà ouvert par `module-info.java` (`requires fr.nedjar.vigiechiro.audio`). **Rien à installer.**
- **Ce qu'il affiche** : en haut la **forme d'onde** (amplitude au fil du temps), en bas le **spectrogramme / sonogramme** (fréquence × temps, la couleur = l'intensité) — l'outil de lecture des cris de chauves-souris. Sa barre d'outils intègre la **lecture**, le **zoom temporel** (`Temps ±`) et le **zoom fréquentiel** (`Fréq ±`).
- **Boîte noire** : vous n'avez **pas** à lire son code interne (FFT, rendu du spectrogramme...). Vous lui fournissez un fichier audio, il s'occupe du reste.

**Dans le FXML**, c'est un nœud comme un autre — on l'importe par son nom complet, puis on le place :

```xml
<?import fr.nedjar.vigiechiro.audio.AudioView?>
...
<AudioView fx:id="audioView" VBox.vgrow="ALWAYS" />
```

**L'API que vous croiserez** (tout le reste est optionnel) :

| Méthode / propriété | Rôle |
|---|---|
| `setAudioFile(Path)` · `audioFileProperty()` | charge un enregistrement (recalcule onde + spectrogramme) |
| `setSource(String)` | variante : charge depuis une ressource du *classpath* |
| `setPlaying(boolean)` · `togglePlay()` · `playingProperty()` | démarre / arrête la lecture |
| `currentTimeProperty()` · `durationProperty()` | position et durée de lecture (lecture seule) |
| `setTimeZoom(double)` · `setFrequencyZoom(double)` | zoom temporel / fréquentiel (= boutons `Temps ±` / `Fréq ±`) |
| `setTimeExpansionFactor(double)` | facteur d'expansion temporelle (ralentir l'ultrason vers l'audible) |
| `setLightTheme(boolean)` | bascule thème clair / sombre |
| `dispose()` | libère les ressources audio (à la fermeture) |

**Comment l'exercice s'en sert** — l'intégration ci-dessous est **déjà fournie** dans `QualificationController` : on recharge le fichier de la séquence à chaque sélection dans le tableau, et « Écouter » lance la lecture.

```java
chargerAudio("seq-1.wav"); // une séquence par défaut au démarrage
viewModel.sequenceSelectionneeProperty().addListener((obs, ancienne, seq) -> {
  if (seq != null) chargerAudio(seq.getAudioRessource()); // recharge à chaque sélection
});
// ... où chargerAudio(...) se résume à :
audioView.setAudioFile(Path.of(getClass().getResource("/audio/" + ressource).toURI()));
// ... et le bouton « Écouter » (surEcouter) :
audioView.setPlaying(true);
```

Vous n'avez donc **rien à écrire** pour le sonogramme : votre travail porte sur le **ViewModel** et le **câblage MVVM**. `AudioView` est là pour rappeler qu'une IHM réelle **assemble** aussi des composants tiers, sans en connaître les rouages.

### 📋 Les deux idiomes du capstone

```java
// 1. Une propriété dérivée « sur mesure » : un calcul (lambda) + ses dépendances
maPropriete.bind(Bindings.createStringBinding(
    () -> source.get() == null ? "(vide)" : "valeur : " + source.get(),
    source));   // <- la (ou les) propriété(s) dont dépend le calcul, à déclarer

// 2. Relayer la sélection d'une TableView vers le ViewModel
viewModel.selectionProperty().bind(
    table.getSelectionModel().selectedItemProperty());
```

**Travail à faire** : compléter le ViewModel puis le contrôleur. Dix gestes.

*Dans `QualificationViewModel` :*
1. **Décrire la sélection** (`descriptionSelection`) : `"(sélectionnez une séquence dans le tableau)"` si rien n'est sélectionné, sinon `"Séquence HH:mm - XX.X kHz"`. Indice : `Bindings.createStringBinding(..., sequenceSelectionnee)`.
2. **Libeller le verdict** (`verdictGlobalLibelle`) : `"Verdict global : (à saisir)"` tant que le modèle n'a pas de verdict, sinon `"Verdict global : <verdict>"`. Indice : dépend de `nuit.verdictGlobalProperty()`.
3. **`ecouterCommand`** : passer la séquence sélectionnée au statut `"Écoutée"` (si une séquence est sélectionnée).
4. **`enregistrerVerdictCommand`** : recopier le verdict saisi dans le modèle (`nuit.setVerdictGlobal`).

*Dans `QualificationController.initialize()` (le câblage, 8 fils) :*
5. **Configurer les 4 colonnes** (cell value factory) : heure (`HH:mm`), fréquence (`%.1f kHz`), durée (`... s`), statut.
6. **Abonner** la `TableView` à `sequencesProperty()`.
7. **Relayer la sélection** : lier `sequenceSelectionneeProperty()` à la ligne sélectionnée de la table (`getSelectionModel().selectedItemProperty()`).
8. **Lier les libellés** : `labelSelection` ← `descriptionSelectionProperty()` et `labelVerdictGlobal` ← `verdictGlobalLibelleProperty()`.
9. **Désactiver** `boutonEcouter` quand rien n'est sélectionné (`peutEcouterProperty().not()`).
10. **Câbler** le commentaire (`zoneCommentaire` ↔ `commentaireProperty()`) et la **ChoiceBox** (items = `listeVerdicts()`, valeur ↔ `verdictSaisiProperty()`).

**Progression des tests**, à activer un par un :

*Tests du ViewModel (JUnit, sans fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `la_nuit_contient_dix_sequences` | la nuit chargée a 10 séquences (service fourni) | *(rien à faire)* |
| `sans_selection_le_libelle_invite_a_choisir_et_l_ecoute_est_impossible` | description par défaut + écoute impossible | geste 1 |
| `selectionner_une_sequence_active_l_ecoute_et_decrit_la_selection` | la description reflète la sélection | geste 1 |
| `ecouter_passe_la_sequence_selectionnee_au_statut_ecoutee` | `ecouterCommand` | geste 3 |
| `les_trois_verdicts_sont_proposes` | `listeVerdicts` (fourni) | *(rien à faire)* |
| `le_libelle_du_verdict_invite_a_saisir_tant_que_rien_n_est_enregistre` | libellé initial du verdict | geste 2 |
| `enregistrer_un_verdict_met_a_jour_le_libelle` | `enregistrerVerdictCommand` + libellé | gestes 2 + 4 |
| `le_commentaire_est_relie_au_modele` | la propriété commentaire (exposée du modèle) | *(rien à faire)* |

*Tests du contrôleur (TestFX, avec fenêtre) :*

| Test | Ce qui est vérifié | Geste |
|---|---|---|
| `la_table_affiche_les_dix_sequences` | la table est abonnée à la liste | geste 6 |
| `le_bouton_ecouter_est_desactive_sans_selection` | le bouton est désactivé au départ | geste 9 |
| `selectionner_une_ligne_active_le_bouton_et_decrit_la_selection` | sélection relayée + description + bouton actif | gestes 7 + 8 + 9 |
| `ecouter_passe_la_sequence_au_statut_ecoutee` | le bouton Écouter déclenche la commande | gestes 3 + 7 |
| `la_choicebox_propose_les_trois_verdicts` | la ChoiceBox est peuplée | geste 10 |
| `enregistrer_un_verdict_via_la_vue_met_a_jour_le_libelle` | ChoiceBox + bouton + libellé | gestes 4 + 8 + 10 |

> [!NOTE]
> **Deux familles de tests, deux outils**, sur l'écran le plus riche du TP : `QualificationViewModelTest` teste **toute** la logique sans fenêtre (8 tests instantanés), `QualificationControllerTest` ouvre la vue en TestFX (6 tests) pour vérifier le branchement. C'est exactement le découpage que vous reproduirez en SAÉ.

> [!TIP]
> `./mvnw javafx:run` puis [noVNC](https://github.com/IUTInfoAix-R202/tp1#voir-votre-fenêtre-avec-vnc) (port 6080) → **Exercice 7 - Vérifier une nuit (capstone SAE)**. Sélectionnez une ligne : le **sonogramme et le spectrogramme** de la séquence s'affichent (chaque ligne charge un enregistrement différent). Cliquez « Écouter » : le statut passe à « Écoutée » **et l'extrait se joue**. Choisissez un verdict et enregistrez. C'est le parcours P3 de la SAÉ, proprement architecturé.

---

## Exercices bonus

Les bonus sont **facultatifs** et ne comptent pas dans l'autograding. Ils élargissent votre boîte à outils une fois les 7 exercices terminés.

### Bonus 8 - Générateur de mème (MVVM + Canvas)

**Objectif** : écrire des catchphrases sur un **vrai template de mème**, et voir que la **logique de présentation** (transformer en MAJUSCULES) vit dans le ViewModel (testable), tandis que le **dessin** (image de fond, polices, contour sur un `Canvas`) reste dans la vue.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Générateur de mème (MVVM + Canvas)](src/main/resources/assets/maquette_meme.svg)

**Le rendu final** (ici avec un texte d'exemple) :

<img alt="Résultat attendu - Bonus 8 : le mème dessiné sur un template (image de fond + texte blanc cerné de noir)" src=".github/assets/apercu-bonus8-meme.png" width="430"/>

**Ce que vous allez découvrir** : des propriétés **dérivées** (`texteHautAffiche`, `texteBasAffiche`) en MAJUSCULES, et un `Canvas` redessiné à chaque changement : le contrôleur dessine d'abord l'**image de fond** (le template), puis les légendes en **blanc cerné de noir** (le look classique des mèmes).

> 📚 **Cours** : [CM4 #16](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#16) (le ViewModel, classe pure) et [CM4 #24](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#24) (le contrôleur câblage) - ici la logique de présentation (MAJUSCULES) vit dans le ViewModel, le dessin reste dans la vue.

**Le matériel fourni** dans `fr.univ_amu.iut.bonus8` :
- `MemeViewModel.java` : à **compléter** (les deux versions majuscules dérivées).
- `MemeController.java` + `MemeView.fxml` + `MemeApp.java` : **fournis complets** (les champs bidirectionnels, et le dessin du `Canvas` : image de fond + texte cerné).
- `meme-fond.jpg` : le **template du mème** (l'image de fond), **fourni**.
- `MemeViewModelTest.java` : **3 tests JUnit purs**.

**À implémenter** : dans `MemeViewModel`, lier `texteHautAffiche` / `texteBasAffiche` aux saisies, en majuscules. Indice : `Bindings.createStringBinding(() -> ....toUpperCase(...), source)`.

> [!NOTE]
> Pas de test d'interface ici : le résultat (les majuscules) est **dessiné sur un `Canvas`**, donc non vérifiable par un test automatique. La logique est entièrement couverte par `MemeViewModelTest`. Lancez l'app (`./mvnw javafx:run` → **Bonus 8**) pour voir le mème se dessiner en temps réel.

### Bonus 9 - Note de terrain (un ViewModel, plusieurs vues)

**Objectif** : constater le bénéfice « un ViewModel, plusieurs vues » : une seule propriété `note`, éditée dans une zone de saisie, **prévisualisée** en direct ailleurs, et **comptée** dans une troisième zone. Tout se synchronise sans une ligne de code de synchronisation.

### Maquette à reproduire

Voici l'interface que vous devez construire :

![Maquette Note de terrain (un ViewModel, plusieurs vues)](src/main/resources/assets/maquette_note_terrain.svg)

**Le rendu final** (la même note alimente les trois vues : la saisie, l'aperçu en direct et le compteur de caractères) :

<img alt="Résultat attendu - Bonus 9 : une zone de saisie, un aperçu en direct et un compteur de caractères, tous liés au même ViewModel" src=".github/assets/apercu-bonus9-note.png" width="548"/>

**Ce que vous allez découvrir** : trois widgets (`TextArea` + 2 `Label`) liés au **même** ViewModel ; une propriété **dérivée** (le compteur de caractères) que la vue affiche.

> 📚 **Cours** : [CM4 #22](https://iutinfoaix-r202.github.io/cours/cm4-mvvm-persistance.html#22) - « **Plusieurs vues, un seul ViewModel** », exactement le bénéfice illustré par ce bonus.

**Le matériel fourni** dans `fr.univ_amu.iut.bonus9` :
- `NoteTerrainViewModel.java` : à **compléter** (le compteur dérivé).
- `NoteTerrainController.java` + `NoteTerrainView.fxml` + `NoteTerrainApp.java` : **fournis complets** (les trois liaisons sur le même ViewModel).
- `NoteTerrainViewModelTest.java` : **2 tests JUnit purs**.

**À implémenter** : dans `NoteTerrainViewModel`, lier `nombreCaracteres` à la longueur de la note, au format `"N caractères"`. Indice : `Bindings.concat(note.length(), ...)`.

> [!TIP]
> `./mvnw javafx:run` → **Bonus 9 - Note de terrain (multi-vues)**. Tapez dans la zone de saisie : l'aperçu et le compteur suivent **en temps réel**, sans une ligne de synchronisation. C'est tout le bénéfice d'un ViewModel partagé.

---

*IUT d'Aix-Marseille - Département Informatique - 2026*
