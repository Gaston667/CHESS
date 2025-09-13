package reseau;

public enum TypeMessage {
    DEMANDE_DE_PARTIE, // Un joueur veut lancer une partie
    MOUVEMENT,         // Transmission d'un coup
    FIN_DE_PARTIE,     // Signal de fin

    SYSTEMEBLEU,           // Message système ( info serveur, etc.)
    SYSTEMEROUGE,         // Message système ( info serveur, etc.) en rouge
    CHAT               // Message chat entre joueurs
}
    