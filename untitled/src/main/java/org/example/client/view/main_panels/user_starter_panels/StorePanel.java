package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.order.Order;
import org.example.server.model.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;

public class StorePanel extends JPanel implements ActionListener {
    private Client client;
    private LinkedList<Item> items = new LinkedList<>();
    private Boolean[] couldBuy = new Boolean[7];
    private JButton item0;
    private JButton item1;
    private JButton item2;
    private JButton item3;
    private JButton item4;
    private JButton item5;
    private JButton item6;
    private JButton exchange;
    private JButton back;

    public StorePanel(Client client) {
        this.client = client;
        client.setStorePanel(this);
        Order order = new Order(Order.OrderType.RESET_ITEM , client.getUsername());
        client.send(order);

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        item0 = new JButton("BUY");
        this.add(item0);
        item0.setBounds(80 ,  430 , 60 , 30);
        item0.setFocusable(false);
        item0.addActionListener(this);

        item1 = new JButton("BUY");
        this.add(item1);
        item1.setBounds(225 ,  430 , 60 , 30);
        item1.setFocusable(false);
        item1.addActionListener(this);

        item2 = new JButton("BUY");
        this.add(item2);
        item2.setBounds(370 ,  430 , 60 , 30);
        item2.setFocusable(false);
        item2.addActionListener(this);

        item3 = new JButton("BUY");
        this.add(item3);
        item3.setBounds(515 ,  430 , 60 , 30);
        item3.setFocusable(false);
        item3.addActionListener(this);

        item4 = new JButton("BUY");
        this.add(item4);
        item4.setBounds(660 ,  430 , 60 , 30);
        item4.setFocusable(false);
        item4.addActionListener(this);

        item5 = new JButton("BUY");
        this.add(item5);
        item5.setBounds(805 ,  430 , 60 , 30);
        item5.setFocusable(false);
        item5.addActionListener(this);

        item6 = new JButton("BUY");
        this.add(item6);
        item6.setBounds(950 , 430 , 60 , 30);
        item6.setFocusable(false);
        item6.addActionListener(this);

        exchange = new JButton("Exchange");
        this.add(exchange);
        exchange.setBounds(360 , 620 , 100 , 50);
        exchange.setFocusable(false);
        exchange.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds( 620 , 620 , 100 , 50);
        back.setFocusable(false);
        back.addActionListener(this);

    }
    public void paintItems(Graphics g){
        g.setFont(new Font("Courier", Font.BOLD, 30));
        String coin = "COIN : " + client.getCoin();
        g.drawString(coin , 100 , 100);
        String diamond = "DIAMOND : " + client.getDiamond();
        g.drawString(diamond , 550 , 100);

        g.setColor(Color.PINK);
        g.fillRect(50 , 300 , 120 , 120);
        g.setColor(Color.BLUE);
        g.fillRect(195 , 300 , 120 , 120);
        g.setColor(Color.RED);
        g.fillRect(340 , 300 , 120 , 120);
        g.setColor(Color.CYAN);
        g.fillRect(485 , 300 , 120 , 120);
        g.setColor(Color.YELLOW);
        g.fillRect(630 , 300 , 120 , 120);
        g.setColor(Color.ORANGE);
        g.fillRect( 775 , 300 , 120 , 120);
        g.setColor(Color.MAGENTA);
        g.fillRect(920 , 300 , 120 , 120);
    }
    public void paintItemType(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.BOLD, 10));
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            Date now = new Date();
            String display = "";
            if(now.after(item.getStartDate())){
                if(now.after(item.getEndDate())){
                    couldBuy[i] = false;
                    display = "The item is out of date";
                }else {
                    couldBuy[i] = true;
                    display = String.valueOf(item.getNumberSoldPerPlayer());
                }
            }else {
                couldBuy[i] = false;
                display = "The date of the item has not yet been reached";
            }
            g.drawString(display , 60 + i*145 , 280);
            g.drawString(String.valueOf(item.getItemType()), 60 + i*145 , 255);
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintItems(g);
        paintItemType(g);
    }
    public void handelItem(int i){
        int option = showOptions();
        if(option != -1){
            if(couldBuy[i]) {
                client.send(new Order(Order.OrderType.BUY_ITEM, items.get(i), option));
            }else {
                JOptionPane.showMessageDialog(this, "This item is not available due to its date");
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(item0)){
            handelItem(0);
        } else if (actionEvent.getSource().equals(item1)) {
            handelItem(1);
        } else if (actionEvent.getSource().equals(item2)) {
            handelItem(2);
        } else if (actionEvent.getSource().equals(item3)) {
            handelItem(3);
        } else if (actionEvent.getSource().equals(item4)) {
            handelItem(4);
        } else if (actionEvent.getSource().equals(item5)) {
            handelItem(5);
        } else if (actionEvent.getSource().equals(item6)) {
            handelItem(6);
        } else if (actionEvent.getSource().equals(exchange)) {
            client.send(new Order( Order.OrderType.EXCHANGE));
        } else if (actionEvent.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }
    public int showOptions(){
        if(client.isOnline()) {
            Object[] options = {"coin", "diamond"};
            int n = JOptionPane.showOptionDialog(null,
                    "Choose a payment method",
                    "pay",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    options,
                    options[1]);
            return n;
        }else {
            JOptionPane.showMessageDialog(this, "you are offline");
            return -1;
        }
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }
}
