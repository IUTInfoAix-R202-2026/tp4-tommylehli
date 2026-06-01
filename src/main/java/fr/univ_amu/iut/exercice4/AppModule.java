package fr.univ_amu.iut.exercice4;

import com.google.inject.AbstractModule;
import fr.univ_amu.iut.exercice3.ServiceAuth;
import fr.univ_amu.iut.exercice3.ServiceAuthSimple;

/**
 * Module Guice de l'exercice 4.
 *
 * <p>À l'exercice 3, c'est {@code FormulaireConnexionApp} qui choisissait l'implémentation concrète
 * du service ({@code new ServiceAuthSimple()}) et la passait à la main. Ici, on déclare ce choix
 * <b>une bonne fois pour toutes</b> dans un module Guice. C'est la documentation vivante de
 * l'architecture : on lit le câblage de l'application en un seul fichier.
 *
 * <p>{@code ConnexionViewModel} et {@code ConnexionController} ne sont pas déclarés ici : Guice
 * sait les construire automatiquement (constructeur {@code @Inject} / champ {@code @Inject}) une
 * fois qu'il connaît l'implémentation de {@link ServiceAuth}.
 */
public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    // TODO exercice 4 : lier l'interface ServiceAuth à son implémentation.
    //
    // Objectif : quand une classe demande un ServiceAuth (via @Inject), Guice
    // doit fournir une instance de ServiceAuthSimple.
    // Astuce : bind(ServiceAuth.class).to(ServiceAuthSimple.class);
    bind(ServiceAuth.class).to(ServiceAuthSimple.class);
  }
}
