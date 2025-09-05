package eightqueens;

import java.awt.*;

/** Ферзь, который самостоятельно ищет приемлемую позицию.
 */
public class Queen {  
    
    /* =========================== Свойства =============================== */

    /* ------------------------ Доска-владелец --------------------------- */
    private final Desk _owner;

    /* ------------------------ Позиция на доске --------------------------- */
    private Point _pos;

    public int row() {
        return _pos.y;
    }

    public int col() {
        return _pos.x;
    }

    public Point pos() {
        return _pos;
    }

    /* --------------------------- Сосед слева ---------------------------- */
    private final Queen _leftNeighbor;
    
    

    /* =========================== Операции =============================== */

    /* ---------------------------- Порождение ---------------------------- */
    public Queen(Desk owner, int col, int row, Queen neighbor) {
        _owner = owner;
        _pos = new Point(col, row);
        _leftNeighbor = neighbor;
    }
    
    /* -------------------------- Поиск решения --------------------------- */
    
    /** Ищет НОВУЮ приемлемую позицию для себя.
    *
    * @return признак того, что позиция найдена
    */
    public boolean findNewAcceptablePosition() {
        boolean isFind = false;

        // Ищем позицию собственными силами...
        // Пока позиция не найдена и не вышли за пределы доски
        {
            // Делаем шаг "вверх"
        }

        // Если не удалось найти позицию собственными силами
        {
            // Просим соседа слева найти приемлемую позицию для себя, если он имеется

            // Переходим в позицию под доской и снова ищем собственную позицию, если сосед нашел новую позицию
        }

        return isFind;
    }
    
    
    /** Атакует ли ферзь или его соседи слева указанную позицию.
     * 
     * @param col номер столбца (1...owner.colCount())
     * @param row номер строки (1...owner.rowCount(), owner.posAboveDesk(), owner.posBelowDesk())
     * @return признак того, что ферзь и его соседи слева атакуют указанную позицию
     */ 
    public boolean canAttack(int col, int row) {
        boolean isAttack;
        
        // Атакует ферзь
        isAttack = (this.row() != _owner.rowAboveDesk())
                    && (row != _owner.rowBelowDesk())
                    && (this.col() == col || this.row() == row
                        || Math.abs(this.col()-col) == Math.abs(this.row() - row));
        
        // Атакуют ли соседи, если ферзь не атакует
        if(!isAttack && _leftNeighbor != null)
        { isAttack = _leftNeighbor.canAttack(col, row); }
        
        return isAttack;
    }
    
    
    
    /* -------------------------- Отрисовка --------------------------- */
    
    /** Отрисовка ферзя
     * @param g графический контекст, в котором происходит отрисовка ферзя.
    */
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((col()-1)*Desk.CELL_SIZE+3, (_owner.rowCount() - row())*Desk.CELL_SIZE+3,
                    Desk.CELL_SIZE-6, Desk.CELL_SIZE-6);
    }
}