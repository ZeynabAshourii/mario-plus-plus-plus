package org.example.client.view.main_panels.online_game;

import org.example.client.controller.Client;
import org.example.order.Order;
import org.example.server.model.Item;
import org.example.server.model.SpecialBag;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
public class EditBagPanel extends JPanel implements ActionListener {
    private Client client;
    private SpecialBag specialBag;
    private JButton firstItem;
    private JButton secondItem;
    private JButton thirdItem;
    private JButton fourthItem;
    private JButton fifthItem;
    private JButton back;
    private LinkedList<Item> items = new LinkedList<>();
    private int whichBag;
    public EditBagPanel(Client client, SpecialBag specialBag , int whichBag) {
        this.client = client;
        this.specialBag = specialBag;
        this.whichBag = whichBag;
        client.setEditBagPanel(this);
        client.send(new Order(Order.OrderType.OPEN_BAG , client.getUsername()));


        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        firstItem = new JButton("Fist item");
        this.add(firstItem);
        firstItem.setBounds(100 , 70 , 180 , 60);
        firstItem.setFocusable(false);
        firstItem.addActionListener(this);

        secondItem = new JButton("Second item");
        this.add(secondItem);
        secondItem.setBounds(100 , 180 , 180 , 60);
        secondItem.setFocusable(false);
        secondItem.addActionListener(this);

        thirdItem = new JButton("Third item");
        this.add(thirdItem);
        thirdItem.setBounds(100 , 290 , 180 , 60);
        thirdItem.setFocusable(false);
        thirdItem.addActionListener(this);

        fourthItem = new JButton("Fourth item");
        this.add(fourthItem);
        fourthItem.setBounds(100 , 400 , 180 , 60);
        fourthItem.setFocusable(false);
        fourthItem.addActionListener(this);

        fifthItem = new JButton("Fifth item");
        this.add(fifthItem);
        fifthItem.setBounds(100 , 510 , 180 , 60);
        fifthItem.setFocusable(false);
        fifthItem.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(440 , 600 , 200 ,80 );
        back.setFocusable(false);
        back.addActionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        for (int i = 0; i < 5; i++){
            String text = "";
            Item item = specialBag.getItems()[i];
            if(item == null){
                text = "null";
            }else {
                if(item.getItemType() == null){
                    text = "null";
                }else {
                    text = String.valueOf(item.getItemType());
                }
            }
            g.drawString(text , 300 , 110*i + 90);
        }
        g.setFont(new Font("Courier", Font.BOLD, 10));
        g.setColor(Color.BLACK);

        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            String text = item.getItemType() + " : " + item.getNumberAvailable();
            g.drawString(text , 60 + i*145 , 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(firstItem)){
            chooseItem(specialBag.getItems()[0] , 0);
        } else if (actionEvent.getSource().equals(secondItem)) {
            chooseItem(specialBag.getItems()[1] , 1);
        } else if (actionEvent.getSource().equals(thirdItem)) {
            chooseItem(specialBag.getItems()[2] , 2);
        } else if (actionEvent.getSource().equals(fourthItem)) {
            chooseItem(specialBag.getItems()[3] , 3);
        } else if (actionEvent.getSource().equals(fifthItem)) {
            chooseItem(specialBag.getItems()[4] , 4);
        } else if(actionEvent.getSource().equals(back)){
            client.getClientFrame().setContentPane(new OnlineGamePanel(client));
        }
    }
    public void chooseItem(Item item , int i){
        Object[] options = {"invisible", "speed" , "heal" , "explosive bomb" , "speed bomb" , "hammer" , "null"};
        int option = JOptionPane.showOptionDialog(null,
                "Choose a payment method",
                "item type",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[6]);
        client.send(new Order(Order.OrderType.EDIT_BAG ,i , whichBag , option ));
    }
    public boolean isEmptyItem(Item item){
        if(item == null){
            return true;
        }else if (item.getItemType() == null){
            return true;
        }
        return false;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void setSpecialBag(SpecialBag specialBag) {
        this.specialBag = specialBag;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }
}
