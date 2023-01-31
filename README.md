# <p align = center> Tank-stars </p>

> A clone of the popular Android/iOS game *Tank Stars* for Desktop using the cross-platform **Java** game development framework **LibGDX** using **object-oriented** and **event-driven** programming.

> This game was made under **Prof. Raghava Mutharaju** as a part of our Advanced Programming course (CSE 201) in Monsoon'22 semester, IIIT Delhi.

---

The application supports the following features:
- 1 vs 1 game mode where tanks face each other and take turns to shoot each other until one of them runs out of health.
- The player is allowed to select the power and angle of the trajectory and then fire the shot.
- The effect of the hit (impact on the health) on the tank is based on how close it has been hit.
- The affected tank moves in the direction it has been hit. Also, the amount of movement is decided on how accurate the hit was.
- Allows players to choose from 3 tanks before starting the game.
- There is a pause menu that allows players to save or resume or exit to the main menu at any point in the game.
- Players are able to save the game state and save the following:
  1. Health of the 2 players  
  2. Store the exact position of the tanks
  3. Store the orientation of the ground.
 - A player is able to save as well as load any saved game.
 - The game allows multiple save/load games [Upto 4, currently].
 - The ground in the game are generated **completely randomly** in every new game.
 ---
 
- All the principles of Object oriented programming have been used in making of this game, namely, Inheritance, Polymorphism, Abstraction, Encapsulation and we have tried to follow the best coding practices (naming conventions, access modifiers for class, fields, comments etc).
- Utilized the **Serializable** interface in Java to save the games.
- Followed 2 design patterns while making this game.

---

To run the game, download and setup a dev environment for LibGDX from the following link: [LibGDX, Setup a dev environment](https://libgdx.com/wiki/start/setup)
- Follow the steps on the above link 
- clone this repository 
- import the project using libGDX and your IDE
- Run the DesktopLauncher.Java file


## Made with :heart: by [Ashutosh Gera](https://github.com/Ashutosh-Gera) and [Kartik Singhal](https://github.com/kksinghal)

