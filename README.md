# Lab 2
## Task 1: Reorganize

In this task, we focused on reorganizing the project repository and improving unit tests for the `StandardDevice` class using Test-Driven Development (TDD) with Mockito functionalities.

We restructured and introduced new unit tests, incorporating various Test Double types such as Dummy, Stub, Mock, and Spy. All tests are available in the file: **reorganise/ReorganiseDeviceTest.java**.

## Task 3: Reengineer

In this task, we extended a small app with a GUI, implementing a logging feature to display relevant messages. The application now consists of three classes: GUI, Logic and Log.

### GUI Application

The GUI application, available in the folder **oopGUIExam**, allows users to interact with a grid of cells following specific rules:

1. Click on a cell to incrementally number it.
2. Select non-adjacent cells; on selecting an adjacent cell, all numbered cells move top-right.
3. Continue clicking to move all numbered cells further top-right.
4. The application closes if a click causes a numbered cell to exit the grid.

### Log Class

We introduced a Log class to the project, available in the file **oopGUIExam/Log.java**, responsible for printing relevant messages:

1. "newMark" - when a new marker is added.
2. "moved" - when markers are translated.
3. "isOver" - when a marker exits the borders.

### Integration Testing
All file tests are available in the folder: **test/oopGUIExam**.
Integration tests were conducted to ensure seamless interactions between classes:

1. **Logic - Log**
   - Checked Log class prints "newMark" when Logic hit method is called.
   - Checked Log class prints "moved" after adding two adjacent markers.
   - Checked Log class prints "isOver" when a marker moves outside the borders.

2. **GUI - Logic**
   - Verified correct invocation of Logic hit method when a button is clicked.

3. **GUI - Logic - Log**
   - Checked Log class prints "newMark" on clicking a button in the GUI.
   - Checked absence of "newMark" print on clicking another button.
   - Checked Log class prints "moved" on clicking two adjacent buttons.
