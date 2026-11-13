# Viva Preparation Guide

## Key questions to answer
- Why should database work not run on the EDT?
- Why is `==` wrong for string comparison?
- Why is `Thread.sleep()` a poor choice for GUI responsiveness?
- What is the purpose of `SwingWorker`?
- Why do we need `PreparedStatement`?
- Why does `getGraphics()` not preserve drawings?
- Why does a unique database constraint help with duplicates?

## Short answers
1. The EDT handles repainting and events; blocking it makes the UI freeze.
2. Strings must be compared using `.equals()`.
3. `SwingWorker` keeps UI responsive while background operations run.
4. `PreparedStatement` prevents SQL injection and keeps user input safe.
5. `getGraphics()` paints only to the current display surface; the drawing is not stored.
