
package chatty.gui.components.settings;

import chatty.gui.Highlighter;
import chatty.gui.components.LinkLabel;
import chatty.lang.Language;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Presets for Highlighting prefixes and stuff.
 * 
 * @author tduva
 */
public class MatchingPresets extends JDialog {
    
    public MatchingPresets(SettingsDialog d) {
        super(d);
        setTitle("Presets");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        
        gbc = d.makeGbc(0, 0, 1, 1);
        add(new LinkLabel("<html><body style='width:340px;padding:4px;'>" + SettingsUtil.getInfo("info-matching_presets.html", null),
                d.getLinkLabelListener()), gbc);
        
        gbc = d.makeGbc(0, 1, 1, 1);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        ListSelector setting = d.addListSetting("matchingPresets", "Presets", 100, 250, false, true);
        setting.setDataFormatter(input -> input.trim());
        setting.setInfoLinkLabelListener(d.getLinkLabelListener());
        setting.setTester(CommandSettings.createCommandTester());
        setting.setChangeListener(value -> {
            HighlighterTester.testPresets = Highlighter.HighlightItem.makePresets(value);
        });
        add(setting, gbc);
        
        JButton closeButton = new JButton(Language.getString("dialog.button.close"));
        closeButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        gbc = d.makeGbc(0, 5, 2, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(closeButton, gbc);
        
        pack();
        setMinimumSize(getPreferredSize());
    }
    
}
