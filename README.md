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
  - Elementi in movimento, come: 
    - veicoli: generano le collisioni e devono essere evitati per completare il livello
    - tronchi: utilizzati come vettori per spostarsi e attraversare i corsi d'acqua.
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

Gli elementi costitutivi il problema sono sintetizzati nella seguente figura.

```mermaid
classDiagram
    class Match {
        +start() void
    }
    class GameObjectControllable  {
        +int xPosition
        +int yPosition
        +int getXPosition()
        +int getYPosition()
        +void move(KeyCode code)
    }
    class Frog {
        +int lives
        +void collectToken()
        +int getLives()
    }
    class Lane {
        +int speed
        +int direction
    }
    class GameObjectNotControllable{
        +int xPosition
        +int yPosition
        +int getXPosition()
        +int getYPosition()
        +void updatePosition()
    }
    class Obstacle {
    }
    class Log {
    }
    class Token {
        +void applyEffect(Frog)
    }
    Match -- GameObjectControllable
    Match -- Lane
    GameObjectNotControllable <|-- Obstacle
    GameObjectNotControllable <|-- Token
    GameObjectNotControllable <|-- Log
    GameObjectControllable <|-- Frog
    Lane *-- GameObjectNotControllable : composed of

```

Ogni classe definisce un comportamento specifico:
- **Match**: coordinatore principale del flusso di gioco. Responsabile dell’inizio del match e del collegamento tra le varie entità.
- **Frog**: rappresenta il personaggio principale controllato dal giocatore e fornisce le funzioni di movimento e raccolta dei gettoni.
- **Lane**: definisce una corsia di gioco con una velocità e una direzione specifiche composta da elementi non controllabili.
- **GameObjectNotControllable**: classe base per gli oggetti non controllabili. Possono essere ostacoli, tronchi o gettoni.
- **Obstacle**: definisce gli elementi dinamici del percorso che determinano le collisioni.
- **Token**: introduce elementi strategici attraverso effetti bonus.
- **Log**: rappresenta un elemento in movimento che può essere utilizzato come mezzo di trasporto tra 2 sponde.

Questa struttura consente una chiara separazione delle responsabilità, facilitando la manutenzione e l'espansione del progetto.

---

## Design

### Architettura

Il design del gioco segue il pattern MVC (Model-View-Controller), una scelta che garantisce modularità e facilita l'evoluzione del progetto. Ogni componente ha ruoli ben definiti:

```mermaid
classDiagram

class Match {
    +start() void
}

class MatchView {
    +renderFrog(frog: Frog) void
    +renderLane(lane: Lane) void
    +updateFrogPosition(frog: Frog) void
    +renderGameOver(score: int) void
}

class ViewObserver {
    +onMove(direction: String) void
}

class MatchController {
    +handleInput(input: String) void
    +updateView() void
}

MatchController <|-- Match
MatchController <|-- ViewObserver
MatchController "1" -- "1..*" MatchView : interacts with

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

##### Problema

Nel gioco, la rana si muove lungo le corsie evitando gli ostacoli o saltando su tronchi. Diventa quindi essenziale:
- verificare la validità di ogni movimento (non fuori dai bordi dello schermo o su una posizione occupata da un ostacolo).
- rilevare le collisioni con oggetti dinamici (es. veicoli).
- garantire fluidità nel gameplay mantenendo un'implementazione efficiente.

##### Soluzione

Il movimento è gestito dalla classe ```Frog```, che calcola la nuova posizione in base alla direzione ricevuta. La classe ```CollisionDetector``` verifica se la posizione è valida utilizzando bounding boxes. Questa separazione migliora la modularità: ```Frog``` si occupa solo del movimento, mentre il ```CollisionDetector``` si concentra sulla logica di interazione.

##### Motivazioni

Separare il movimento e il rilevamento delle collisioni migliora la leggibilità e facilita i test unitari.
L’uso del sistema di bounding boxes per le collisioni è stato scelto per la sua semplicità ed efficienza.

```mermaid
classDiagram
    class Frog {
        +int lives
        +void collectToken()
        +int getLives()
    }
    class CollisionDetector {
        +boolean checkCollision(GameObjectNotControllable, Frog)
    }
    class GameObjectNotControllable{
        +int xPosition
        +int yPosition
        +int getXPosition()
        +int getYPosition()
        +void updatePosition()
    }
    class Obstacle {
        +void updatePosition()
    }
    class Log {
        +void updatePosition()
    }
    Frog --> CollisionDetector : usa
    CollisionDetector -- GameObjectNotControllable
    GameObjectNotControllable <|-- Obstacle
    GameObjectNotControllable <|-- Log
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