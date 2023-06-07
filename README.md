# Testing Game Unina

## Repository per lo sviluppo di un Testing Game

### Requisiti sulla Registrazione ed Autenticazione dei Giocatori (Task 2-3)

Le specifiche assegnate riguardano la registrazione e l'autenticazione di un generico studente nei confronti del Testing Game. L'applicazione di autenticazione è stata progettata utilizzando il pattern architetturale MVC (Model-View-Controller) e sviluppata utilizzando i framework SpringBoot e SpringSecurity, insieme a Spring Data MongoDB e SpringMVC. Come applicativo di build-automation è stato utilizzato il plugin di Maven. In aggiunta, l'applicazione fornisce il servizio di ChangePassword nel caso di password dimenticata o se la si volesse cambiare. Inoltre l'applicazione utilizza anche Java Mail Sender per inviare delle email all'utente contente dei link cliccabili per confermare l'identità dello studente nel caso di registrazione e per accede alla schermata di immissione nuova password nel caso la si voglia cambiare. L'avvenuta autenticazione permette allo studente loggato di poter accedere ad una dashboard per poter giocare una nuova partita oppure visualizzare lo storico delle partite giocate.

### [Documentazione](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki)

### [Filesystem](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki/Documentazione-Filesystem)

### [Riferimento API](https://github.com/Testing-Game-SAD-2023/T23-G7/wiki/API)
