package reseau;

public enum TypeMessage {
    // Partie / connexion
    DEMANDE_DE_PARTIE,
    ACCEPTATION_PARTIE,
    REFUS_PARTIE,
    INIT_PLATEAU,

    // Jeu
    MOUVEMENT,
    ABANDON,
    ANNULATION,
    TEMPS_DEPASSER,

    // Fin
    DEFAITE,
    VICTOIRE,
    EGALITE,

    // Communication
    CHAT,
    SYSTEME_INFO,
    SYSTEME_WARN,
    SYSTEME_ERR
}
