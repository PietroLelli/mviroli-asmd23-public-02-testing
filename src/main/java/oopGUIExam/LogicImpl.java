package oopGUIExam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final int size;
    private List<Position> marks = new LinkedList<>();
    private boolean moving = false;
    private Log logger;

    public LogicImpl(int size, Log log) {
        this.size = size;
        logger = log;
    }

    @Override
    public Optional<Integer> hit(Position position) {
        if (this.isOver()) {
            return Optional.empty();
        }
        if (this.moving || startMoving(position)) {
            this.moving = true;
            logger.moved();
            this.moveMarks();
            return Optional.empty();
        }
        this.marks.add(position);
        logger.newMark(position);
        return Optional.of(this.marks.size());
    }

    private boolean neighbours(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
    }

    private boolean startMoving(Position position) {
        return this.marks.stream().anyMatch(p -> neighbours(p, position));
    }

    private void moveMarks() {
        this.marks = this.marks
                .stream()
                .map(p -> new Position(p.x() + 1, p.y() - 1))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<Integer> getMark(Position position) {
        return Optional.of(this.marks.indexOf(position)).filter(i -> i >= 0).map(i -> i + 1);
    }

    @Override
    public boolean isOver() {
        boolean isOver = this.marks.stream().anyMatch(p -> p.x() == this.size || p.y() == -1);
        if (isOver) {
            logger.isOver();
        }
        return isOver;
    }
}
