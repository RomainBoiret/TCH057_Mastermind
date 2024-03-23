# Application Android Mastermind

Il s'agit d'une application mobile pour jouer au jeu de Mastermind. Le jeu consiste à deviner un code secret composé de couleurs dans un ordre spécifique. Après chaque proposition, le joueur reçoit des indications sur le nombre de couleurs correctes et bien placées, ainsi que le nombre de couleurs correctes mais mal placées. Le joueur utilise ces indications pour déduire le code secret en un nombre limité d'essais.

## Auteurs

- [Romain Boiret](https://github.com/RomainBoiret)
- [Martin Rolo Dussault](https://github.com/Martinrolo)
- [Félix Caron](https://github.com/FelixCaronn)

## Date de création

18 Mars 2024

## Écrans

- **Écran d'accueil :** Accueille les utilisateurs et leur permet de naviguer vers les écrans de jeu, de configuration ou d'historique. Les utilisateurs doivent saisir leur adresse e-mail pour accéder au jeu.
- **Écran de jeu :** L'écran principal pour jouer au Mastermind. Les utilisateurs peuvent faire des propositions, voir les indications et gérer l'état du jeu.
- **Écran de configuration :** Permet aux utilisateurs de configurer les paramètres du jeu, tels que la longueur du code, le nombre de couleurs et le nombre maximal d'essais.
- **Écran d'historique :** Affiche une liste des parties précédentes, y compris l'e-mail du joueur, le code secret, le résultat du jeu et le nombre d'essais.

## Détails de l'implémentation

- **Architecture :** Suit les composants d'architecture Android standard.
- **Structure du code :** Organisée en packages pour les activités, les modèles, les présentateurs, les adaptateurs et les objets d'accès aux données (DAO).
- **Bibliothèques :** Utilise des bibliothèques telles que Jackson pour le traitement JSON et OkHttp pour la communication avec l'API REST.
- **Couleurs :** Représente les couleurs sous forme d'entiers au format RGB et fournit une correspondance pour les 8 couleurs utilisées dans le jeu.
- **Logique du jeu :** Utilise une classe Code pour représenter les codes, une classe Feedback pour fournir des indications sur les propositions, et une classe Mastermind pour gérer les sessions de jeu.

## API et base de données

- **API REST :** Fournit les couleurs, les codes secrets et les statistiques du jeu. Utilise JSON-server pour le développement.
- **Base de données SQLite :** Stocke les données locales du jeu, y compris le code secret, l'e-mail du joueur, le résultat du jeu et le nombre d'essais.

## Installation

Clonez le dépôt et ouvrez le projet dans Android Studio. Exécutez le projet sur un émulateur ou un appareil physique pour jouer au Mastermind.