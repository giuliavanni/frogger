# Relazione del Progetto: Frogger

## Analisi

Il progetto consiste nella realizzazione di un videogioco ispirato al classico arcade *Frogger*. Si propone di realizzare una reinterpretazione del titolo originale, integrando meccaniche di gioco classiche con elementi di design innovativi per attrarre un pubblico ampio e diversificato. L'obiettivo principale è di guidare una rana attraverso diverse corsie trafficate evitando ostacoli, raccogliendo bonus strategici e completando il percorso entro un tempo limite.

Il gioco è strutturato su livelli con difficoltà progressiva. Le meccaniche principali includono un sistema di raccolta oggetti, un timer dinamico e la possibilità di salvare i punteggi dei giocatori per promuovere una sana competitività.

---

## Requisiti

### Requisiti funzionali
- **[RF1] Schermata Menù**: Offre le seguenti opzioni:
  - `New Game`: Inizia una nuova partita e reimposta i progressi.
  - `Settings`: Permette di regolare impostazioni come il volume, la difficoltà del gioco e la modalità a schermo intero.
  - `Quit`: Consente di uscire dall'applicazione in modo semplice e immediato.

- **[RF2] Inserimento Nome Giocatore**: Una schermata dedicata permette al giocatore di inserire il proprio nome prima dell'inizio della partita, garantendo che il punteggio venga salvato correttamente e possa essere confrontato nella classifica.

- **[RF3] Modalità di Gioco Classica**:
  - La rana si muove attraverso diverse corsie utilizzando i tasti freccia per navigare.
  - Ostacoli in movimento, come veicoli (generano le collisioni) o tronchi (utilizzati come vettori per spostarsi), che devono essere evitati per completare il livello senza collisioni.
  - Possibilità di raccogliere gettoni che forniscono bonus, come estensioni del tempo o vite extra.

- **[RF4] Sistema di Collisione**:
  - La partita termina se la rana collide con un ostacolo o se il tempo a disposizione si esaurisce. Viene mostrata una schermata di `Game Over` con il punteggio ottenuto.

- **[RF5] Countdown Temporale**: Un timer visibile in alto a schermo tiene traccia del tempo rimanente. Il conto alla rovescia aggiunge tensione e strategia al gameplay.

- **[RF6] Schermata di `Game Over`**:
  - Visualizza il punteggio raggiunto e offre opzioni per tornare al menù principale o iniziare una nuova partita.

### Funzionalità opzionali
- Livelli con difficoltà crescente che aumentano progressivamente la velocità e la densità degli ostacoli, introducendo nuove sfide a ogni progresso.
- Tronchi mobili per attraversare corsi d'acqua e altre meccaniche di navigazione strategica.
- Una classifica persistente per mostrare i migliori punteggi ottenuti dai giocatori.
- Condivisione sui social media dei risultati della partita, favorendo il coinvolgimento della community.
- Modalità di gioco personalizzabili con opzioni avanzate nel menù delle impostazioni.

---

## Analisi e modello del dominio

Il dominio di *Frogger* si basa su un insieme di entità principali, ciascuna con responsabilità e comportamenti specifici. Queste entità, come la rana, le corsie, gli ostacoli e i gettoni, lavorano insieme per creare una dinamica di gioco coinvolgente e strategica.

```mermaid
classDiagram
    class Player {
        +String name
        +int score
        +int lives
        +void collectToken()
    }
    class Frog {
        +int xPosition
        +int yPosition
        +void move(direction)
    }
    class Lane {
        +int speed
        +int direction
    }
    class Obstacle {
    }
    class Log {
    }
    class Token {
        +void applyEffect(Player)
    }
    class GameObjectControllable  {
    }
    class GameObjectNotControllable{
        +int xPosition
        +int yPosition
    }
    Player -- Frog
    GameObjectNotControllable <|-- Obstacle
    GameObjectNotControllable <|-- Token
    GameObjectNotControllable <|-- Log
    GameObjectControllable <|-- Frog
    GameObjectControllable -- Lane
    Lane *-- GameObjectNotControllable : composta da
```

Ogni classe definisce un comportamento specifico:
- **Player** gestisce punteggi, vite e interazioni con bonus.
- **Frog** rappresenta il personaggio principale controllato dal giocatore e fornisce le funzioni di movimento.
- **Lane** e **Obstacle** definiscono gli elementi dinamici del percorso e la loro interazione.
- **Token** introduce elementi strategici attraverso effetti bonus.

Questa struttura consente una chiara separazione delle responsabilità, facilitando la manutenzione e l'espansione del progetto.

---

## Design

### Architettura

Il design del gioco segue il pattern MVC (Model-View-Controller), una scelta che garantisce modularità e facilita l'evoluzione del progetto. Ogni componente ha ruoli ben definiti:

```mermaid
classDiagram
    class Model {
        +Player player
        +List~Lane~ lanes
        +void updateGameState()
    }
    class View {
        +void renderGame()
        +void displayMenu()
    }
    class Controller {
        +void handleInput()
        +void updateView()
    }
    Model -- Controller
    View -- Controller
```

- **Model**: Si occupa della logica del gioco e dello stato delle entità, come la posizione della rana e il timer.
- **View**: Rende l'esperienza visiva, occupandosi del disegno grafico e dell'interfaccia utente.
- **Controller**: Gestisce l'input dell'utente, traducendolo in azioni sul Model e aggiornamenti della View.

### Design dettagliato

#### Gestione del Timer
**Problema**: Garantire un sistema di countdown efficace e sincronizzato con il gameplay.

**Soluzione**: Implementazione di un thread dedicato che aggiorna periodicamente il timer e notifica la View per aggiornare il display.

```mermaid
classDiagram
    class Timer {
        -int timeRemaining
        +void start()
        +void stop()
        +void decrement()
    }
    class View {
        +void updateTimerDisplay(int time)
    }
    Timer -- View
```

#### Collision Detection
**Problema**: Assicurare un rilevamento accurato delle collisioni tra rana e ostacoli dinamici.

**Soluzione**: Utilizzo di bounding boxes per verificare le intersezioni tra coordinate, implementando un sistema efficiente e riutilizzabile.

```mermaid
classDiagram
    class CollisionDetector {
        +boolean checkCollision(Frog, Obstacle)
    }
    class Frog {
        +int x
        +int y
        +int width
        +int height
    }
    class Obstacle {
        +int x
        +int y
        +int width
        +int height
    }
    CollisionDetector -- Frog
    CollisionDetector -- Obstacle
```

---

## Sviluppo

### Testing automatizzato

Per garantire la qualità del codice, sono stati implementati test automatizzati utilizzando JUnit. Questi test coprono vari aspetti del gioco, tra cui:
- **Collisioni**: Verifica della corretta gestione degli impatti tra rana e ostacoli.
- **Timer**: Controllo della precisione del countdown e della sua sincronizzazione.
- **Gestione dei Punteggi**: Validazione del calcolo e della memorizzazione dei punteggi.

Esempio di test JUnit:
```java
@Test
public void testCollisionDetection() {
    Frog frog = new Frog(10, 10, 10, 10);
    Obstacle obstacle = new Obstacle(15, 15, 10, 10);
    CollisionDetector detector = new CollisionDetector();
    assertTrue(detector.checkCollision(frog, obstacle));
}
```

### Note di sviluppo

#### Vanni
- Creazione grafica degli sprite.
- Implementazione del posizionamento dinamico degli ostacoli.
- Gestione del timer e del punteggio.

#### Rambaldi
- Implementazione del movimento della rana.
- Sviluppo della logica di collisione.
- Persistenza dei dati per i punteggi.

---

## Guida utente

1. Avviare il gioco e scegliere un'opzione dal menù principale (`New Game`, `Settings`, `Quit`).
2. Inserire il proprio nome per salvare i progressi.
3. Utilizzare i tasti freccia per muovere la rana e attraversare le corsie.
4. Raccogliere gettoni per bonus di tempo o vite extra.
5. Alla fine della partita, visualizzare il punteggio e scegliere se continuare o uscire.

---

