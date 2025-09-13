package reseau;

public enum TypeMessage {
    DEMANDE_DE_PARTIE, // Un joueur veut lancer une partie
    MOUVEMENT,         // Transmission d'un coup
    FIN_DE_PARTIE,     // Signal de fin

    SYSTEME,           // Message système (erreur, info serveur, etc.)
    CHAT               // Message chat entre joueurs
}
    