package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DSourceListITunes {

    public static SourceList createSourceList() {
        Icon musicPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunesMusicPlaylist.png"));
        Icon moviesPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunesMoviesPlaylist.png"));
        Icon tvShowsPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunesTVShowsPlaylist.png"));
        Icon podcastsPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunes7PodcastsPlaylist.png"));
        Icon purchasedPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunes7PurchasedPlaylist.png"));
        Icon playlistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunes7Playlist.png"));
        Icon smartPlaylistIcon =
                new ImageIcon(DSourceListITunes.class.getResource("./icons/MBiTunes7SmartPlaylist.png"));

        final SourceListModel model = new SourceListModel();

        SourceListCategory libraryCategory = new SourceListCategory("Library");
        SourceListCategory storeCategory = new SourceListCategory("Store");
        SourceListCategory playlistCategory = new SourceListCategory("Playlists");

        model.addCategory(libraryCategory);
        model.addCategory(storeCategory);
        model.addCategory(playlistCategory);

        model.addItemToCategory(new SourceListItem("Music", musicPlaylistIcon), libraryCategory);
        model.addItemToCategory(new SourceListItem("Movies", moviesPlaylistIcon), libraryCategory);
        model.addItemToCategory(new SourceListItem("TV Shows", tvShowsPlaylistIcon), libraryCategory);
        model.addItemToCategory(new SourceListItem("Podcasts", podcastsPlaylistIcon), libraryCategory);

        model.addItemToCategory(new SourceListItem("Purchased", purchasedPlaylistIcon), storeCategory);

        model.addItemToCategory(new SourceListItem("My playlist", playlistIcon), playlistCategory);
        model.addItemToCategory(new SourceListItem("My smart playlist", smartPlaylistIcon), playlistCategory);

        return new SourceList(model);
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
