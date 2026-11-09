# Screenshot status

Genuine screenshots were captured from the application running on Tomcat 11.0.27 with MySQL 8.4.9:

- `01-welcome-page.png`
- `02-game-page.png`
- `03-result-page.png`
- `04-leaderboard.png`
- `06-validation-example.png`

`05-database-table.png` is not included because no MySQL GUI was available. To capture it, open MySQL Workbench, connect to the configured `math_whiz_db` database, run:

```sql
SELECT id, player_name, score, difficulty, played_at
FROM game_scores
ORDER BY played_at DESC;
```

Capture the actual result grid and save it as `05-database-table.png` in this folder. Do not replace it with a mockup or generated image.
