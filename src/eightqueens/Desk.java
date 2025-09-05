package eightqueens;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

/** Класс доски, содержащий 8 ферзей (модель и представление).
*/
public class Desk extends JPanel implements Iterable<Queen>{

    /* ========================== Константы =============================== */

    static final int CELL_SIZE = 30;
    
    /* =========================== Свойства =============================== */
    
    /** Размеры доски (в количестве клеток).
    */
    public int colCount(){
        return 8;
    }
    public int rowCount(){
        return 8;
    }
    
    public int rowAboveDesk() {
        return rowCount()+1;
    }
    public int rowBelowDesk() {
        return 0;
    }

    public Point abovePos(Point p) {
        Point ap = null;
        if(p.y < rowAboveDesk()) {
            ap = new Point(p.x, p.y+1);
        }
        return ap;
    }

    public Point belowPos(Point p) {
        Point bp = null;
        if(p.y > rowBelowDesk()) {
            bp = new Point(p.x, p.y-1);
        }
        return bp;
    }

    /* Список ферзей */    
    private Queen[] _queens;

    public Queen getQueen(int col) {

        for(Queen q : _queens) {

            if(q.col() == col ) {
                return q;
            }
        }

        return null;
    }

    public int queenCount() {
        return _queens.length;
    }

    @Override
    public Iterator<Queen> iterator() {
        return Arrays.stream(_queens).iterator();
    }


    /** Номер решения, т.е. очередной расстановки фигур */
    private int _solutionNumber = 0;
    public int getSolutionNumber() {
        return _solutionNumber;
    }

    /* =========================== Операции =============================== */

    /* ---------------------------- Порождение ---------------------------- */

    private EightQueensWindow _owner;

    /** Порождает доску со стандартным расположением ферзей
    */     
    public static Desk buildStandartDesk(EightQueensWindow owner) {
        Desk d = new Desk();
        d.setQueens( d.initQueensPos() );

        d._owner = owner;
        d._owner.add(d);

        return d;
    }
    
    public Desk( ) {
        // Настраиваем визуальное отображение доски
        setBounds(0, 0, colCount()*CELL_SIZE, rowCount()*CELL_SIZE);
        setPreferredSize(new Dimension(colCount()*CELL_SIZE, rowCount()*CELL_SIZE) );
        setBackground(Color.white);                
    }
    
    private Point[] initQueensPos() {
        Point[] queens_pos = new Point[ colCount() ];
        
        for(int i = 0; i < colCount(); i++) {
            queens_pos[i] = new Point( i+1, rowBelowDesk() );
        } 
        
        return queens_pos;
    }       
    
    public void setQueens(Point[] queens_pos) {
        
        // Расставляем ферзей
        _queens = new Queen[queens_pos.length];
        
        // Устанавливаем первого ферзя
        _queens[0] = new Queen(this, queens_pos[0].x, queens_pos[0].y, null);

        // Устанавливаем остальных ферзей
        for( int i = 1; i < queens_pos.length; i++  ) {
           int col = queens_pos[i].x;
           int row = queens_pos[i].y;
           
           _queens[i] = new Queen(this, col, row, _queens[i-1]);
        }
    }
    
    
    /* -------------------------- Поиск решения --------------------------- */
    
    /** Ищет первое решение и отображает его
    */
    public boolean firstSolution()
    {
        boolean isOk = true;
        for(Queen q : _queens)
        {
            isOk = isOk && q.findNewAcceptablePosition();
        }

        if(isOk) {
            _solutionNumber++;
            repaint();
        } else {
            _owner.freeze();
        }

        return isOk;
    }

    /** Ищет новое решение и отображает его
    */
    public void newSolution()
    {
        boolean isOk = _queens[_queens.length-1].findNewAcceptablePosition();

        if(isOk) {
            _solutionNumber++;
            repaint();
        } else {
            _owner.freeze();
        }
    }
    
    /* -------------------------- Отрисовка --------------------------- */
    
    /** Отрисовывает доску
    */
    @Override
    public void paint(Graphics g)
    {
        // отрисовка черных клеток
        super.paint(g);
        g.setColor(Color.black);
        for(int col=0; col < colCount(); col++)
        {
            for(int row=0; row < rowCount()/2; row++)
            {
                g.fillRect(row*2*CELL_SIZE+CELL_SIZE-CELL_SIZE*(col%2), col*CELL_SIZE, 
                            CELL_SIZE, CELL_SIZE);
            }
        }
        
        // отрисовка ферзей
        for(Queen q: this._queens){
            q.paint(g);
        }
    }
}
