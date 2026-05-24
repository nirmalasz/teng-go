# Teng-Go!
<img width="1020" height="757" alt="image" src="https://github.com/user-attachments/assets/d5767a1c-eba5-4a40-9fd4-ee206540a18d" />

**Play directly here:** https://nirmalasz.itch.io/teng-go

Teng-Go! is an action-survival-corporate game built with Java and LibGDX framework. In this rogue-like gameplay, you as the main character have to escape the Office by killing the enemies when the work time is done. But it is not that easy to defeat the enemies, so you'll have to accept blessing from Karl Marx and upgrade your stats on your Desk.

Control:
- W/A/S/D: move UP, LEFT, DOWN, RIGHT
- SPACE: TELEPORT
- LEFT CLICK: ATTACK
- ESC: PAUSE


## System Architecture
1. Client Tier - frontend: with Java and LibGDX
2. Application Tier - backend API: built with Spring Boot hosted in Railway
3. Data Layer - database: Using online PostgreSQL with NeonDB, stores normalized data for `players`, `scores`, and `achievements`.

# Diagram
## ER Diagram

<img width="3232" height="2016" alt="image" src="https://github.com/user-attachments/assets/908dcefe-11c4-42e9-a38c-e00960c9452f" />

## Game Flowchart

<img width="4224" height="5280" alt="image" src="https://github.com/user-attachments/assets/25ebe0e6-4b50-4218-b6ca-bf780edbac79" />

## System Diagram

<img width="3232" height="1504" alt="image" src="https://github.com/user-attachments/assets/d41b1050-6114-4907-89a6-dd7f8d7a028e" />

## Software Design Patterns

This project implements 6 design pattern:
1. **Singleton Pattern**
2. **State Pattern**
3. **Observer Pattern**
4. **Strategy Pattern**
5. **Facade Pattern** 
6. **Object Pool Pattern**
7. **Command Pattern**

## To Play the Game
Download the exe file. And you can immediatel play it!

## Game Assets Credit
- character-templates-pack: https://erisesra.itch.io/character-templates-pack
- metal-ui: https://github.com/czyzby/gdx-skins/tree/master/metal
- modern-interior: https://limezu.itch.io/moderninteriors
