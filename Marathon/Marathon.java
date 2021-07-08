import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LinkedList{
    Runner head; 
    Runner tail;
    Runner hmOne,hmTwo,orOne,orTwo,gdOne,gdTwo;
    int Length;
    private String hm1,hm2,or1,or2,gd1,gd2;

    public int getLength() {
        return Length;
    }

    public String getHm1() {
        return hm1;
    }

    public String getHm2() {
        return hm2;
    }

    public String getOr1() {
        return or1;
    }

    public String getOr2() {
        return or2;
    }

    public String getGd1() {
        return gd1;
    }

    public String getGd2() {
        return gd2;
    }
    
    
    LinkedList()
    {
        head = null;
        tail = null;
        
        hmOne = null;
        hmTwo = null;
        orOne = null;
        orTwo = null;
        gdOne = null;
        gdTwo = null;
        
        Length = 0;
        
        hm1 = "2,80,000/-";
        hm2 = "2,10,000/-";
        or1 = "1,90,000/-";
        or2 = "1,50,000/-";
        gd1 = "1,35,000/-";
        gd2 = "1,15,000/-";
        
    }
    
    void insert(Runner node)
    {
        Length++; 
        if (head==null)
        {
            head = node;
            
            tail = node;
        }
        else{
            Runner cur = head;
            Runner next = cur.getNext();
            if (next == null){
                if (node.getTime()>cur.getTime())
                    cur.setNext(node);
                else if (node.getTime() <= cur.getTime()) {
                    node.setNext(cur);
                    head = node;
                }
            }
            else if(node.getTime()<=cur.getTime()){
                node.setNext(cur);
                head = node;
            }
            else{
                while(next!=null){
                    if (cur.getTime()<= node.getTime() && next.getTime()>node.getTime()){
                        cur.setNext(node);
                        node.setNext(next);
                        break;
                    }
                    cur = next;
                    next = next.getNext();   
                }
                if(next == null){
                    cur.setNext(node);
                }
            }
        }
    }
    
    void Ranking(){
        Runner cur = head;
        hmOne = null;
        hmTwo = null;
        orOne = null;
        orTwo = null;
        gdOne = null;
        gdTwo = null;
        
        while(cur!=null){
            if (cur.getCat() == 1){
                if (hmOne == null){
                    hmOne = cur;
                }
                else if(hmTwo == null){
                    hmTwo = cur;
                }
            }
            else if (cur.getCat() == 2){
                if (orOne == null) {
                    orOne = cur;
                } else if (orTwo == null) {
                    orTwo = cur;
                }
            }
            else {
                if (gdOne == null) {
                    gdOne = cur;
                } else if (gdTwo == null) {
                    gdTwo = cur;
                }
            }
            cur = cur.getNext();
        }
    }
    void print(){
        Runner cur = head ; 
        while (cur.next!=null){
            System.out.print(cur.getTime()+" ");
            cur = cur.getNext();
        }
        System.out.println(cur.getTime());
    }
}
class Runner{
    private String name;
    private int time;
    private int cat;
    Runner next;
    
    Runner(String n,int t,int cate){
        name = n;
        time = t;
        cat = cate;
        next = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public Runner getNext() {
        return next;
    }

    public void setNext(Runner next) {
        this.next = next;
    }
}
public class Marathon{
    public static void main(String[] args){
        LinkedList rl = new LinkedList();
        
        Font f = new Font("TimesRoman",Font.BOLD,22);
        
        JFrame frame = new JFrame("Runners");
       
        JPanel p_main = new JPanel();
        p_main.setLayout(new BoxLayout(p_main,BoxLayout.Y_AXIS));
        
        JPanel p_name = new JPanel();
        p_name.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_main.add(p_name);
        
        JLabel l_name = new JLabel("Name  ");
        l_name.setFont(f);
        JTextField tf_name = new JTextField();
        tf_name.setPreferredSize(new Dimension(150,40));
        p_name.add(l_name);
        p_name.add(tf_name);
        
        JPanel p_time = new JPanel();
        p_time.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_main.add(p_time);
        
        JLabel l_time = new JLabel("Time (in mins) ");
        l_time.setFont(f);
        JTextField tf_time = new JTextField();
        tf_time.setPreferredSize(new Dimension(100,40));
        p_time.add(l_time);
        p_time.add(tf_time);
        
        JPanel p_category=  new JPanel();
        p_category.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel l_category = new JLabel("Choose Category  : ");
        l_category.setFont(f);
        p_category.add(l_category);

        p_main.add(p_category);
        
        ButtonGroup bg_category = new ButtonGroup();

        JRadioButton rb_hm = new JRadioButton("Half Marathon (20 Km)");
        bg_category.add(rb_hm);
        JRadioButton rb_or = new JRadioButton("Open 10K Run (10 Km)");
        bg_category.add(rb_or);        
        JRadioButton rb_gd = new JRadioButton("Great Delhi Run (5 Km)");
        bg_category.add(rb_gd);

        p_category.add(rb_hm);
        p_category.add(rb_or);
        p_category.add(rb_gd);
        
        JPanel p_buttons = new JPanel();
        p_buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton b_next = new JButton("Next");
        JButton b_win = new JButton("Winner");
        JButton b_cancel = new JButton("Cancel");
                
        p_buttons.add(b_next);
        p_buttons.add(b_win);
        p_buttons.add(b_cancel);
        p_main.add(p_buttons);
        
        b_cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        b_next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tf_name.getText();
                String temptime = tf_time.getText();
                if (temptime.equals("") || name.equals("")){
                    JFrame error = new JFrame("Error");
                    
                    JPanel p_error = new JPanel();
                    p_error.setLayout(new BoxLayout(p_error, BoxLayout.Y_AXIS));
                    
                    JLabel l_error = new JLabel("Please enter corresponding values");
                    l_error.setFont(f);
                    p_error.add(l_error);                    
                    error.add(p_error);
                   
                    
                    JPanel button = new JPanel();
                    button.setLayout(new FlowLayout(FlowLayout.CENTER));
                    JButton reset = new JButton("Re-Enter Values");
                    button.add(reset);
                    error.add(button);
                    reset.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tf_name.setText("");
                            tf_time.setText("");
                            bg_category.clearSelection();
                            error.dispose();
                        }
                    });
                    
                    error.setSize(300, 100);
                    error.setVisible(true);
                    error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else{
                    int time = Integer.parseInt(temptime);
                    int cat;
                    if (rb_hm.isSelected())
                        cat = 1;
                    else if (rb_or.isSelected())
                        cat = 2;
                    else
                        cat = 3;
                    Runner temp = new Runner(name,time,cat);
                    rl.insert(temp);
                    bg_category.clearSelection();
                    tf_name.setText(null);
                    tf_time.setText(null);
                    
                    // System.out.println(rl.getLength());
                    // rl.print();
                }
            }
        });
        
        b_win.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rl.Ranking();
                JFrame winFrame = new JFrame("Winners");
                
                JPanel p_main = new JPanel();
                p_main.setLayout(new BoxLayout(p_main, BoxLayout.Y_AXIS));
                
                JPanel p_winboxgdr = new JPanel();
                p_winboxgdr.setLayout(new BoxLayout(p_winboxgdr, BoxLayout.Y_AXIS));

                JPanel p_gdr = new JPanel();
                JPanel p_first_gdr = new JPanel();
                JPanel p_second_gdr = new JPanel();
                
                JLabel gdr = new JLabel("Great Delhi Run");
                gdr.setFont(f);
                p_gdr.add(gdr);
                
                p_first_gdr.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel win_gdr = new JLabel("Winner");
                JLabel w_prz_gdr = new JLabel("   Prize");                
                JTextField out_win_gdr = new JTextField();
                JTextField win_prz_gdr = new JTextField();
                out_win_gdr.setPreferredSize(new Dimension(100, 20));
                win_prz_gdr.setPreferredSize(new Dimension(100, 20));
                if (rl.gdOne == null)
                    out_win_gdr.setText("");
                else
                    out_win_gdr.setText(rl.gdOne.getName());
                win_prz_gdr.setText(rl.getGd1());
                out_win_gdr.setEditable(false);
                win_prz_gdr.setEditable(false);
                
                p_second_gdr.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel rup_gdr = new JLabel("Runner up");
                JLabel r_prz_gdr = new JLabel("   Prize");
                JTextField out_rup_gdr = new JTextField();
                JTextField rup_prz_gdr = new JTextField();
                out_rup_gdr.setPreferredSize(new Dimension(100, 20));
                rup_prz_gdr.setPreferredSize(new Dimension(100, 20));
                if (rl.gdTwo==null)
                    out_rup_gdr.setText("");
                else
                    out_rup_gdr.setText(rl.gdTwo.getName());
                rup_prz_gdr.setText(rl.getGd2());
                rup_prz_gdr.setEditable(false);
                out_rup_gdr.setEditable(false);
                
                p_first_gdr.add(win_gdr);
                p_second_gdr.add(rup_gdr);                
                
                p_first_gdr.add(out_win_gdr);
                p_second_gdr.add(out_rup_gdr);
                
                p_first_gdr.add(w_prz_gdr);
                p_second_gdr.add(r_prz_gdr);
                
                p_first_gdr.add(win_prz_gdr);
                p_second_gdr.add(rup_prz_gdr);

                p_winboxgdr.add(p_gdr);
                p_winboxgdr.add(p_first_gdr);
                p_winboxgdr.add(p_second_gdr);
                
                p_main.add(p_winboxgdr);
                
                JPanel p_winboxor = new JPanel();
                p_winboxor.setLayout(new BoxLayout(p_winboxor, BoxLayout.Y_AXIS));
                
                JPanel p_or = new JPanel();
                JPanel p_first_or = new JPanel();
                JPanel p_second_or = new JPanel();
                JLabel or = new JLabel("Open 10K Run");
                or.setFont(f);
                p_or.add(or);
              
                p_first_or.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel win_or = new JLabel("Winner");
                JLabel w_prz_or = new JLabel("   Prize");                
                JTextField out_win_or = new JTextField();
                JTextField win_prz_or = new JTextField();
                out_win_or.setPreferredSize(new Dimension(100, 20));
                win_prz_or.setPreferredSize(new Dimension(100, 20));
                if (rl.orOne == null)
                        out_win_or.setText("");
                else
                    out_win_or.setText(rl.orOne.getName());                
                win_prz_or.setText(rl.getOr1());
                
                out_win_or.setEditable(false);
                win_prz_or.setEditable(false);
              
                p_second_or.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel rup_or = new JLabel("Runner up");
                JLabel r_prz_or = new JLabel("   Prize");                
                JTextField out_rup_or = new JTextField();
                JTextField rup_prz_or = new JTextField();
                out_rup_or.setPreferredSize(new Dimension(100, 20));
                rup_prz_or.setPreferredSize(new Dimension(100, 20));
                if (rl.orTwo==null)
                    out_rup_or.setText("");
                else
                    out_rup_or.setText(rl.orTwo.getName());
                rup_prz_or.setText(rl.getOr2());
                out_rup_or.setEditable(false);                
                rup_prz_or.setEditable(false);
              
                p_first_or.add(win_or);
                p_second_or.add(rup_or);
                
                p_first_or.add(out_win_or);
                p_second_or.add(out_rup_or);
                
                p_first_or.add(w_prz_or);
                p_second_or.add(r_prz_or);                
                
                p_first_or.add(win_prz_or);
                p_second_or.add(rup_prz_or);
             
                p_winboxor.add(p_or);
                p_winboxor.add(p_first_or);
                p_winboxor.add(p_second_or);
                
                p_main.add(p_winboxor);
                
                JPanel p_firstboxhm = new JPanel();
                p_firstboxhm.setLayout(new BoxLayout(p_firstboxhm, BoxLayout.Y_AXIS));
                
                JPanel p_hm = new JPanel();
                JPanel p_first_hm = new JPanel();
                JPanel p_second_hm = new JPanel();
                JLabel hm = new JLabel("Half Marathon");
                hm.setFont(f);
                p_hm.add(hm);
              
                p_first_hm.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel first_hm = new JLabel("Winner");
                JLabel w_prz_hm = new JLabel("  Prize");                
                JTextField out_first_hm = new JTextField();
                JTextField first_prz_hm = new JTextField();
                out_first_hm.setPreferredSize(new Dimension(100, 20));
                first_prz_hm.setPreferredSize(new Dimension(100, 20));
                if (rl.hmOne == null)
                    out_first_hm.setText("");       
                else
                    out_first_hm.setText(rl.hmOne.getName());
                first_prz_hm.setText(rl.getHm1());
                
                out_first_hm.setEditable(false);                
                first_prz_hm.setEditable(false);
              
                p_second_hm.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel second_hm = new JLabel("Runner up");
                JLabel r_prz_hm = new JLabel("   Prize");                
                JTextField out_second_hm = new JTextField();
                JTextField second_prz_hm = new JTextField();
                out_second_hm.setPreferredSize(new Dimension(100, 20));
                second_prz_hm.setPreferredSize(new Dimension(100, 20));
                if (rl.hmTwo == 	null)
                    out_second_hm.setText("");
                else
                    out_second_hm.setText(rl.hmTwo.getName());
                second_prz_hm.setText(rl.getHm2());
                
                out_second_hm.setEditable(false);
                second_prz_hm.setEditable(false);
              
                p_first_hm.add(first_hm);
                p_second_hm.add(second_hm);
                
                p_first_hm.add(out_first_hm);
                p_second_hm.add(out_second_hm);
                
                p_first_hm.add(w_prz_hm);
                p_second_hm.add(r_prz_hm);                
                
                p_first_hm.add(first_prz_hm);                                          
                p_second_hm.add(second_prz_hm);
              
                p_firstboxhm.add(p_hm);
                p_firstboxhm.add(p_first_hm);
                p_firstboxhm.add(p_second_hm);
                
                p_main.add(p_firstboxhm);
                
                winFrame.add(p_main);
                winFrame.setSize(600, 600);
                frame.setVisible(false);
                winFrame.setVisible(true);
                winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });  
        frame.add(p_main);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}