# Testing Game Unina

# Tasks 2 & 3 : Requisiti sulla Registrazione ed Autenticazione dei Giocatori

## Gruppo 7 
* Luca Comentale M63001520
* Carmine De Monaco M63001414
* Emanuele Di Maio M63001427
* Roberta Di Marino M63001523
* Giovanni Dioguardi M63001418

## Requisiti sulla Registrazione ed Autenticazione dei Giocatori (Task 2-3)
L’applicazione deve consentire agli studenti di registrarsi per poter conservare la storia delle attività svolte, oppure per accedere a requisiti
di gioco più complessi. All’atto della registrazione, lo studente fornirà nome, cognome, un indirizzo email valido ed una password, il sistema dopo aver controllato la validità dei dati forniti, aggiungerà il giocatore all’elenco dei giocatori registrati e gli assocerà un Id univoco. Sarebbe
desiderabile raccogliere anche altre informazioni sugli studenti, come il corso di studi a cui sono iscritti (Bachelor, Master Degree, o altro).
All’atto della autenticazione, lo studente fornirà l’indirizzo email fornito per la registrazione e la relativa password, il sistema dopo aver
controllato la validità dei dati forniti, autenticherà il giocatore e gli fornirà una schermata per l’accesso alle funzionalità di gioco o di consultazione delle sessioni di gioco passate.

## Preview dei dettagli implementativi
L'applicazione di autenticazione è stata progettata utilizzando il pattern architetturale MVC (Model-View-Controller) e sviluppata utilizzando i framework SpringBoot e SpringSecurity, insieme a Spring Data MongoDB e SpringMVC. Come applicativo di build-automation è stato utilizzato il plugin di Maven. In aggiunta, l'applicazione fornisce il servizio di ChangePassword nel caso di password dimenticata o se la si volesse cambiare. Inoltre l'applicazione utilizza anche Java Mail Sender per inviare delle email all'utente contente dei link cliccabili per confermare l'identità dello studente nel caso di registrazione e per accede alla schermata di immissione nuova password nel caso la si voglia cambiare. L'avvenuta autenticazione permette allo studente loggato di poter accedere ad una dashboard per poter giocare una nuova partita oppure visualizzare lo storico delle partite giocate.


## [Wiki](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki)

## [Filesystem](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki/Documentazione-Filesystem)

## [Riferimento API](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki/API)

## [Tecnologie utilizzate](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki/Tecnologie-utilizzate)
