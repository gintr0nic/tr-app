<p align="center"><img src="https://github.com/mrgian/terremoti-api/raw/master/images/icons/icon.png" height=100></p>
<h1 align="center">TR</h1>
<h3 align="center">
App per informazioni sui ristoranti
</h3>

<p align="center"><img src="https://github.com/mrgian/appquake/raw/master/screenshot/screenshot_2.png" width="32%"><img src="https://github.com/mrgian/appquake/raw/master/screenshot/screenshot_3.png" width="32%"> <img src="https://github.com/mrgian/appquake/raw/master/screenshot/screenshot_4.png" width="32%"></p>

## Descrizione
TR è un'app che ha l'obbiettivo di creare un rete di conoscenze tra consumatori finali, ristoratori, imprese di produzione di materie prime o prodotti trasformati.

Infatti oltre a mettere a disposizione le funzioni classiche per la ricerca e la consultazione delle informazioni sui ristoranti,
permette di conoscere i fornitori dei ristornati, così da poter sostenere piccole e medie imprese e ristoranti che utilizzano materie prime di qualità.

## Funzionalità
- ricerca ristoranti
- lista nuovi ristoranti
- lista ristoranti nelle vicinanze
- mappa dei ristoranti vicini
- lista preferiti
- consultazione menu
- consulatazione info ristorante
- possibilità di conoscere produttori e fornitori di un ristorante

## Account
Esistono tre tipi di account su TR, **utente**, **ristoratore** e **produttore**

La differenza fra utenti e ristoratori o produttori è che questi ultimi hanno la possibilità di modificare le informazioni delle loro pagine, 
che saranno poi visibili agli utenti quando cercano un ristorante o vogliono conoscere il produttore di un ingrediente sul menu.

## Login
Per usare TR è necessario registrarsi tramite email, oppure loggarsi utilizzando uno degli account demo mostrati qui sotto.

Il login tramite Google/Facebook non è al momento disponibile, ma verrà aggiunto prossimamente.

### Account demo
|Tipo        |Email                    |Password                |Nota                                                                                    |
|------------|-------------------------|------------------------|----------------------------------------------------------------------------------------|
|Utente      |`utentedemo@gian.im `    |`demodemo`              |                                                                                        |
|Ristoratore |`ristoratoredemo@gian.im`|`demodemo`              |Ha la possibiltà di modificare la sua pagina con il pulsante in alto a destra nella home|
|Produttore  |`produttoredemo@gian.im` |`demodemo`              |Ha la possibiltà di modificare la sua pagina con il pulsante in alto a destra nella home|

## Backend
TR usa Firebase per il backend.

Authentication viene utlizzato per consentire agli utenti di registrarsi e loggarsi.

Storage viene utlizzato per memorizzare le foto dei ristoranti e produttori.

Firestore Database viene utilizzato per memorizzare:
- il tipo di utente (utente normale, ristoratore o produttore)
- le preferenze dei singoli utenti (per esempio la lista dei ristoranti salvati)
- le info sui ristoranti (nome, descrizione, coordinate, contatti, ecc.)
- il menu dei ristoranti, compreso l'id produttore per ogni singolo ingrediente (per reindirizzare l'utente quando vuole conoscere il produttore)


<img src="https://github.com/mrgian/terremoti-api/raw/master/images/uml/use_case_diagram.png" width="50%">

*Esempio di documento Firestore usato per memorizzare le informazioni su un ristorante*

## To Do

- [ ] Possibiltà per produttori e ristoratori di registrasi autonomamente (con un setup che li guida nella creazione della loro pagina)
- [ ] Login tramite Google o Facebook
- [ ] Recupero password dimenticata
- [ ] Verifica email
