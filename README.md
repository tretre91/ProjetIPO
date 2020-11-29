# Projet IPO
Page github de mon projet d'IPO (Trévis Morvany)

## Liste des états important du projet
- <a href=https://github.com/tretre91/ProjetIPO/tree/7c958be84a27f7002821ec52215f84630e3df924> A la fin de la partie 1 </a>
- <a href=https://github.com/tretre91/ProjetIPO/tree/cb6ad9f6de85df69224d2b6c16d938c1745e44b9> A la fin de la partie 2 </a>
- <a href=https://github.com/tretre91/ProjetIPO/tree/1d119817293dda6547324905bf66d0558cebc770> Première version fonctionnelle de la Partie 3 </a>
- <a href=https://github.com/tretre91/ProjetIPO/tree/eb1c93b18eb235e8bee3344f58c90eae5a79cd33> A la fin de la partie 3 </a>
- Partie 4
  - [X] timer (<a href=https://github.com/tretre91/ProjetIPO/blob/c47aae9ccd6374293faccec56ffd404e27a7fb54/squelette1FRog/src/gameCommons/Game.java>lien</a>)
  - [X] cases spéciales (piège, glace, mur et bonus, <a href=https://github.com/tretre91/ProjetIPO/tree/b70916ff0d620f2181d1fe2d90a95bece126f586/squelette1FRog/src>lien</a>)
  - [X] lignes d'eau (<a href=https://github.com/tretre91/ProjetIPO/tree/4e05830409f266b61057a92087e47c28fcc43d6b>lien</a>)

## Bugs / problèmes
### mineurs
- <s>La grenouille peut toujours bouger après l'apparition de l'écran de fin (<a href=https://github.com/tretre91/ProjetIPO/issues/1>issue #1</a>)</s>
- <s>Les bonus s'affichent sous les rondins, et les murs sous les voitures (<a href=https://github.com/tretre91/ProjetIPO/issues/3>issue #3</a>)</s>
- On perd la partie lorsqu'on essaie d'avancer sur une case *wall* qui contient également une voiture (<a href=https://github.com/tretre91/ProjetIPO/issues/4>issue #4</a>)
### majeurs
- <s>La première version du jeu infini utilise trop de mémoire (toutes les voies qui ont été crées sont gardées en mémoire, donc
 la taille de l'`ArrayList<Lane>` qui contient nos voies augmente indéfiniment) (<a href=https://github.com/tretre91/ProjetIPO/issues/2>issue #2</a>)</s>
