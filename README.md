<div align="center">
<img src="src\main\resources\scenes\logo\logo.png" width=256>

# Unify

<div align="center">

### Domain model

<img alt="ciao" src="src\main\resources\images\domain\domain_model.png" width=512>

<div align=left> 

### IT
<b>Unify</b> è un programma desktop che consente la <b>creazione</b>, <b>l’eliminazione</b> e la <b>navigazione</b> di canzoni, album, artisti e playlist. Ci si può registrare o effettuare l’accesso al sistema con i propri dati. Inoltre, le canzoni possono essere ascoltate grazie al <b>music player</b> presente al suo interno. Presenta un’interfaccia minimale per una maggiore facilità di fruizione del programma, non tralasciando però l’efficacia e l’impatto dell’utilizzo di immagini e testo a schermo.
</div>

<div align=left>
Il programma può funzionare in due diverse modalità:
<ul>

  <li> <b>Modalità user</b>, ossia la modalità di navigazione di un utente normale</li>

  <li><b>Modalità administrator</b>, ossia la modalità di navigazione dell’amministratore del sistema, il quale possiede una serie di privilegi in più rispetto all’utente normale</li>
</ul>

#### MODALITÀ “USER”

Avviando l’applicazione viene mostrata una finestra di login dove poter inserire le credenziali, ossia username e password, al fine di registrarsi al sistema o, qualora l’utente già esistesse, di entrare nel proprio profilo.
Accedendo con credenziali da utente, nella home è possibile visualizzare 3 canzoni causali con immagine della copertina, nome della canzone e nome dell’autore. Avviene lo stesso con tre album, selezionati sempre in modo casuale dall’insieme degli album presenti.
Alla sinistra è presente un menù di selezione per effettuare varie azioni con le seguenti categorie:
 <ul>
  <li> <b>PLAYLIST</b>, dove è possibile creare delle playlist, visualizzarne il nome e l’elenco delle canzoni all’interno. 
  </li>
  <li><b>ALBUM</b>, dove è possibile visualizzare l’elenco di tutti gli album presenti nell’applicazione.</li>
  <li><b>SONG</b>, dove è possibile visualizzare l’elenco di tutte le canzoni presenti nell’applicazione.</li>
  <li><b>ARTIST</b>, dove è possibile visualizzare l’elenco di tutti gli artisti (singoli e band) presenti nell’applicazione.</li>
  <li><b>ACCOUNT</b>, dove è possibile modificare la propria password ma non il proprio nome utente</li>
  </ol>
<br>
<br>

In alto, partendo da sinistra, è presente una icona che permette di tornare in qualsiasi momento alla schermata di home ed una scritta che augura il benvenuto all’utente.
Poco più a destra è, invece, presente una “combobox” che, insieme alla barra di ricerca, permette di filtrare per categoria ciò che si vuole cercare, ed infine l’icona per effettuare il logout dall’account.
Infine, in basso è presente il posto dove saranno poi mostrate le informazioni relative alla canzone riprodotta dal music player.
Accedendo alla sezione dedicata alle PLAYLIST, è possibile visualizzare le playlist attualmente associate all’utente che ha effettuato l’accesso. Inoltre, in alto a destra, è presente un tasto che permette di creare una nuova playlist. Al momento della creazione, viene richiesto di inserire un nome e di selezionare le canzoni da aggiungere alla playlist. Alla fine della creazione della playlist si torna al menù precedente dove è possibile visualizzarne le informazioni premendo sul bottone “View”.
All’interno di questo menù possono essere effettuate le seguenti azioni:
a)	DELETE, che permette di eliminare la playlist in questione.
b)	MODIFY, che ci consente di andare a modificare il nome della playlist, rimuovere delle canzoni al suo interno o di aggiungerne di nuove.
c)	PLAY, che permette di avviare il player ascoltando le canzoni all’interno della playlist
Altrimenti è possibile visualizzare le informazioni relative ad una singola canzone attraverso il tasto “View” presente di fianco il nome e l’artista della canzone stessa.
Selezionando ALBUM, la successiva sezione presente nel menù a sinistra, è possibile accedere alla lista completa degli album disponibili nel sistema.
Cliccando sull’album scelto, è possibile visualizzare le informazioni ad esso relative come il titolo, l’autore e il genere. Inoltre, è anche possibile riprodurre tutte le canzoni dell’album con il bottone “Play all”.
Sempre nel menù a sinistra, cliccando su SONGS, si accede all’elenco di tutte le canzoni salvate nel sistema, dove, come nel caso degli album, è possibile visualizzarne le informazioni relative con tanto di testo della canzone cliccando sulla relativa riga della canzone scelta.
Infine, è presente la sezione ARTIST, dove è possibile visualizzare l’elenco di tutti gli artisti. Scegliendone uno e cliccando su di esso, si possono vedere le foto dell’artista, il nome, i componenti della band (qualora esso lo sia), gli album e le canzoni con reindirizzamento ad esso cliccando sulle scritte in grassetto.
MODALITÀ “ADMINISTRATOR”
Nella modalità “Administrator”, accedendo con username admin e password admin, è possibile effettuare le stesse azioni possibili nella modalità “User” (a parte poter creare delle playlist). Inoltre, dispone di funzionalità ulteriori tali da permettere di modificare gli utenti registrati e di creare, modificare e cancellare artisti, canzoni e album.
Nella sezione “USERS” si può cancellare un utente cliccando sull’icona a forma di cestino, modificarne l’username e la password cliccando sull’icona a forma di foglio con penna o aggiungerne uno.
Infine, nella sezione “SETTINGS” è possibile agire in tre diversi modi:
•	Si possono modificare le informazioni di un artista come le foto, il nome ed eliminare le canzoni e gli album a lui associati. Nello stesso menù è anche possibile aggiungere dei membri ad una band qualora l’artista fosse appunto una band. Si può anche direttamente cancellare un artista o aggiungerne uno attraverso una procedura guidata dove, una volta terminata la creazione dell’artista, che può essere sia un artista singolo che una band, si può scegliere se associargli un album, e conseguentemente delle canzoni, o lasciarlo senza.
•	Si possono modificare le informazioni di un album o cancellarlo. Di un album è possibile modificare l’immagine della cover, il titolo e il genere, o andare ad aggiungere delle canzoni allo stesso.
•	Si possono modificare le canzoni o cancellarle dal sistema. Di una canzone, così come gli album, si può modificare la copertina, il titolo e il genere.
MUSIC PLAYER
Per poter riprodurre una canzone, è possibile cercarla nella barra di ricerca in alto o dall’elenco delle canzoni disponibile e cliccare su play. Verrà mostrato in basso il music player con la cover, il titolo e l’artista della canzone. Inoltre, è presente una scrollbar che permette di spostare il tempo della canzone avanti o indietro tramite un trascinamento della stessa, oppure semplicemente cliccandoci. Per poter navigare tra le canzoni (qualora magari si riproducesse un album con più di una canzone o una playlist) si può cliccare sulle icone di next e previous, mentre per fermare o far ripartire la canzone è presente il tasto play o pause. Sono inoltre presenti i tasti di “shuffle” e di “repeat” che permettono rispettivamente di cambiare casualmente l’ordine delle canzoni riprodotto e di ripetere la canzone attualmente riprodotta. 
INFORMAZIONI GENERALI:
1.	Il programma permette l’inserimento di canzoni unicamente in formato .mp3
2.	Il programma consente l’aggiunta di foto di artisti, canzoni o album unicamente in formato .jpg
</div>



</div>



