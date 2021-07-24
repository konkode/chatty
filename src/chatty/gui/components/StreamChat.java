
package chatty.gui.components;

import chatty.Room;
import chatty.User;
import chatty.gui.MainGui;
import chatty.gui.StyleManager;
import chatty.gui.StyleServer;
import chatty.gui.components.menus.ContextMenuAdapter;
import chatty.gui.components.menus.ContextMenuListener;
import chatty.gui.components.menus.HighlightsContextMenu;
import chatty.gui.components.menus.StreamChatContextMenu;
import chatty.gui.components.textpane.ChannelTextPane;
import chatty.gui.components.textpane.Message;
import chatty.util.api.Emoticon.EmoticonImage;
import chatty.util.api.StreamInfo;
import chatty.util.api.usericons.Usericon;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Simple dialog that contains a ChannelTextPane with stream chat features
 * enabled (optional timeout of messages). Can have messages from several
 * channels redirected to it.
 * 
 * @author tduva
 */
public class StreamChat extends JDialog {
    
    private final ChannelTextPane textPane;
    private final ContextMenuListener contextMenuListener;
    
    public StreamChat(MainGui g, StyleManager styles, ContextMenuListener contextMenuListener,
            boolean startAtBottom) {
        super(g);
        this.contextMenuListener = contextMenuListener;
        setTitle("Stream Chat");

        textPane = new TextPane(g, styles, startAtBottom);
        textPane.setContextMenuListener(new ContextMenuAdapter(contextMenuListener) {
            
            @Override
            public void menuItemClicked(ActionEvent e) {
                if (e.getActionCommand().equals("clearHighlights")) {
                    textPane.clearAll();
                }
                super.menuItemClicked(e);
            }
            
        });
        JScrollPane scroll = new JScrollPane(textPane);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setScrollPane(scroll);
        
        add(scroll, BorderLayout.CENTER);
        
        setSize(400, 200);
    }
    
    public void printMessage(Message message) {
        textPane.printMessage(message);
    }
    
    public void userBanned(User user, long duration, String reason, String id) {
        textPane.userBanned(user, duration, reason, id);
    }
    
    public void setMessageTimeout(int seconds) {
        textPane.setMessageTimeout(seconds);
    }
    
    public void refreshStyles() {
        textPane.refreshStyles();
    }
    
    public void clear() {
        textPane.clearAll();
    }
    
    /**
     * Normal channel text pane modified a bit to fit the needs for this.
     */
    static class TextPane extends ChannelTextPane {
        
        public TextPane(MainGui main, StyleServer styleServer, boolean startAtBottom) {
            // Enables the "special" parameter to be able to remove old lines
            super(main, styleServer, ChannelTextPane.Type.STREAM_CHAT, startAtBottom);
            
            // Overriding constructor is required to set the custom context menu
            linkController.setContextMenuCreator(() -> new StreamChatContextMenu());
        }
        
    }
    
}
