## version 0.9.5 ##
  1. iTunes style table officially introduced via `ITunesTableUI` delegate.
  1. `UnifiedToolBar`, `BottomBar` and `PreferencesTabBar` now have top level classes ([Issue 72](https://code.google.com/p/macwidgets/issues/detail?id=72)).
  1. `SourceList` no longer auto-expands all nodes ([Issue 64](https://code.google.com/p/macwidgets/issues/detail?id=64)).
  1. `PreferencesTabBar` now renders a unified tool bar area on JDK 6 ([Issue 62](https://code.google.com/p/macwidgets/issues/detail?id=62)).
  1. `SourceList` node selection no longer follows clicks to expand a different node ([Issue 67](https://code.google.com/p/macwidgets/issues/detail?id=67)).
  1. Sort icons now show up when using Glazed Lists ([Issue 71](https://code.google.com/p/macwidgets/issues/detail?id=71)).
  1. `HudComboBoxUI` now shows the correct selection after programmatically setting the selected index ([Issue 70](https://code.google.com/p/macwidgets/issues/detail?id=70)).
  1. `SourceListControlBar` now respects minimum and maximum preferred sizes ([Issue 73](https://code.google.com/p/macwidgets/issues/detail?id=73)).
  1. `HudComboBoxUI`'s arrows are now centered correctly when the button grows tall ([Issue 79](https://code.google.com/p/macwidgets/issues/detail?id=79)).
  1. `TriAreaComponent.addComponentToRight` now adds the correct amount of space between components ([Issue 89](https://code.google.com/p/macwidgets/issues/detail?id=89)).
  1. Introduced HUD style radio button via `HudRadioButtonUI` delegate ([Issue 90](https://code.google.com/p/macwidgets/issues/detail?id=90)).
  1. `HudWindow`s are now transparent on Windows ([Issue 35](https://code.google.com/p/macwidgets/issues/detail?id=35)).
  1. `HudWindow`s can now have their close button hidden ([Issue 95](https://code.google.com/p/macwidgets/issues/detail?id=95)).
  1. `SourceListTreeUI` now supports run-time swapping (calls to updateUI work properly) ([Issue 80](https://code.google.com/p/macwidgets/issues/detail?id=80)).
  1. iTunes table does not honor enabled/disabled state ([Issue 86](https://code.google.com/p/macwidgets/issues/detail?id=86)).
  1. `SourceListItems` now automatically trigger a repaint when their data changes (e.g. counter value updated) ([Issue 13](https://code.google.com/p/macwidgets/issues/detail?id=13)).
## version 0.9.4 ##
  1. Unified toolbar now renders correctly on Mac's JDK 6 as well as non Mac platforms ([Issue 24](https://code.google.com/p/macwidgets/issues/detail?id=24)).
  1. `SourceListContextMenuProvider` is now being called correctly on Windows ([Issue 32](https://code.google.com/p/macwidgets/issues/detail?id=32)).
  1. `SourceListTreeUI` now repaints itself when it's parent window's focus state changes ([Issue 34](https://code.google.com/p/macwidgets/issues/detail?id=34)).
  1. `IAppScrollPane` now paints correctly when the focus state of the parent window changes ([Issue 26](https://code.google.com/p/macwidgets/issues/detail?id=26)).
  1. `SourceList` now automatically expands newly added categories and items ([Issue 46](https://code.google.com/p/macwidgets/issues/detail?id=46)).
  1. `IAppScrollBarUI` now better integrates into Swing's UI delegate architecture ([Issue 37](https://code.google.com/p/macwidgets/issues/detail?id=37)).
  1. `IAppScrollBarUI` now correctly paints the scroll buttons as unfocused when the parent window loses focus ([Issue 47](https://code.google.com/p/macwidgets/issues/detail?id=47)).
  1. `SourceListCategory` and `SourceListItem` now allow iteration over their children ([Issue 20](https://code.google.com/p/macwidgets/issues/detail?id=20)).
  1. Demos work on all platforms now ([Issue 33](https://code.google.com/p/macwidgets/issues/detail?id=33)).
  1. Added a System\_Requirements wiki page ([Issue 4](https://code.google.com/p/macwidgets/issues/detail?id=4)).
  1. `SourceList`s can now have their colors customized via `SourceListColorScheme` ([Issue 31](https://code.google.com/p/macwidgets/issues/detail?id=31)).
  1. `SourceList` disclosure triangles now render as white when the corresponding category is selected ([Issue 53](https://code.google.com/p/macwidgets/issues/detail?id=53)).
  1. `SourceListCountBadgeRenderer` now correctly centers badge text ([Issue 56](https://code.google.com/p/macwidgets/issues/detail?id=56)).
  1. `SourceList` now exposes the necessary methods to implement drag and drop ([Issue 50](https://code.google.com/p/macwidgets/issues/detail?id=50)).
  1. `LabeledComponentGroup` now offers a list-based constructor ([Issue 58](https://code.google.com/p/macwidgets/issues/detail?id=58)).
  1. Introduced HUD style UI delegates ([Issue 43](https://code.google.com/p/macwidgets/issues/detail?id=43)).
## version 0.9.3 ##
  1. iApp style scroll bars
  1. `ImageButton` added which allows for buttons with non-rectangular hot-spots to be created from images
  1. `SourceList` selected item not repainting properly on focus change fixed
  1. `SourceList` doesn't automatically expand root node if items are added to the model after it has been made visible
  1. `SourceListContextMenuProvider` introduced which allows developers to easily add custom context menus
  1. `SourceListClickListener` introduced which allows developers to listen for multiple clicks on a `SourceList` item
  1. `SourceList` was ignoring the requested insertion index into the `SourceList` after the `SourceListModel` had been installed
  1. Removed setVisible(true) call from `HUDWindow` constructor
## version 0.9.2 ##
  1. Heads Up Display (HUD)
  1. Works with JGoodies Forms 1.2.1
  1. Dependency on SwingX removed, thus reducing the size from 1.4 MB to 250 KB
  1. `SourceListTreeUI` has been factored out of `SourceList` so that you can apply this UI delegate to any JTree
  1. `EmphasizedLabelUI` now has Javadoc.
## version 0.9.1 ##
    1. Unified Tool Bar
    1. Bottom Bar
    1. Java 6 compatibility on the Mac
    1. Fixed `UnifiedToolBarButtonUI` disabled button masking
    1. Added EXTRA\_SMALL `BottomBarSize` (as seen in Safari's status bar)
## version 0.9.0 (initial release) ##
    1. `Source List`