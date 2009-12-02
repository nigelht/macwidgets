package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class DSourceListMail {

    public static SourceList createSourceList() {
        Icon mailboxIcon =
                new ImageIcon(DSourceListITunes.class.getResource("/com/explodingpixels/macwidgets/icons/mailbox.png"));
        Icon sentIcon =
                new ImageIcon(DSourceListITunes.class.getResource("/com/explodingpixels/macwidgets/icons/sent.png"));
        Icon trashCanIcon =
                new ImageIcon(DSourceListITunes.class.getResource("/com/explodingpixels/macwidgets/icons/trashcan.png"));
        Icon rss =
                new ImageIcon(DSourceListITunes.class.getResource("/com/explodingpixels/macwidgets/icons/rss.png"));

        final SourceListModel model = new SourceListModel();

        SourceListCategory mailboxesCategory = new SourceListCategory("Mailboxes");
        SourceListCategory rssCategory = new SourceListCategory("RSS");

        model.addCategory(mailboxesCategory);
        model.addCategory(rssCategory);

        SourceListItem inboxItem = new SourceListItem("Inbox", mailboxIcon);
        SourceListItem trashCanItem = new SourceListItem("Trash", trashCanIcon);

        model.addItemToCategory(inboxItem, mailboxesCategory);
        model.addItemToCategory(trashCanItem, mailboxesCategory);

        SourceListItem macInbox = new SourceListItem("john.doe@mac.com", mailboxIcon);
        macInbox.setCounterValue(3);
        SourceListItem gmailInbox = new SourceListItem("john.doe@gmail.com", mailboxIcon);
        gmailInbox.setCounterValue(15);

        model.addItemToItem(macInbox, inboxItem);
        model.addItemToItem(gmailInbox, inboxItem);
        model.addItemToItem(new SourceListItem("Sent", sentIcon), inboxItem);
        model.addItemToItem(new SourceListItem("john.doe@mac.com", trashCanIcon), trashCanItem);
        model.addItemToItem(new SourceListItem("john.doe@gmail.com", trashCanIcon), trashCanItem);

        model.addItemToCategory(new SourceListItem("Apple Hot News", rss), rssCategory);
        model.addItemToCategory(new SourceListItem("Exploding Pixels", rss), rssCategory);

        SourceList sourceList = new SourceList(model);
        sourceList.setFocusable(false);

        return sourceList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.add(createSourceList().getComponent(), BorderLayout.CENTER);
                frame.setSize(225, 250);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

            }
        });
    }
}
