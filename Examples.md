### Unified Tool Bar ([Javadoc](http://exploding-pixels.com/google_code/javadoc/com/explodingpixels/macwidgets/UnifiedToolBar.html)) ###
Unified Tool Bars (described [here](http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-BABIFCFJ) in the Apple Human Interface Guidelines) provide a container for the most frequently used tools in your application.  This is most likely what your user will want to use for the main navigation.

![http://exploding-pixels.com/google_code/graphics/UnifiedToolBar.png](http://exploding-pixels.com/google_code/graphics/UnifiedToolBar.png)

**Simple Unified Tool Bar example:**
```
// For some versions of Mac OS X, Java will handle painting the Unified Tool Bar.
// Calling this method ensures that this painting is turned on if necessary.
MacUtils.makeWindowLeopardStyle(frame.getRootPane());

UnifiedToolBar toolBar =  UnifiedToolBar();
JButton button = new JButton("My Button");
button.putClientProperty("JButton.buttonType", "textured");

// Create a new mac button based on the JButton.
AbstractButton macButton = MacButtonFactory.makeUnifiedToolBarButton(button);
// Add the button to the left side of the toolbar.
toolBar.addComponentToLeft(button);

// This is so that the window can be dragged from anywhere on the toolbar.
// This is optional, but will make your Java application feel more like an OSX app.
macMainMenu.installWindowDraggerOnWindow(MainFrame);

// Add the toolbar to the frame.
frame.add(toolBar.getComponent(), BorderLayout.NORTH);
```

### iTunes Table ([Javadoc](http://exploding-pixels.com/google_code/javadoc/com/explodingpixels/macwidgets/MacWidgetFactory.html#createITunesTable(javax.swing.table.TableModel))) ###

Create an iTunes style table like this:

![http://exploding-pixels.com/google_code/graphics/iTunesTable.png](http://exploding-pixels.com/google_code/graphics/iTunesTable.png)
```
String[][] data = new String[][]{
        {"A", "B", "C"},
        {"D", "E", "F"},
        {"G", "H", "I"}};
String[] columnNames = String[]{"One", "Two", "Three"};
TableModel model = new DefaultTableModel(data, columnNames);
JTable table = MacWidgetFactory.createITunesTable(model);
```

you can add sort indicators by doing this:

```
TableUtils.SortDelegate sortDelegate = new TableUtils.SortDelegate() {
        public void sort(int columnModelIndex, TableUtils.SortDirection sortDirection) {
            // initiate your sorting here.
        }
    };
TableUtils.makeSortable(table, sortDelegate);
```

### iApp Scroll Bars ([Javadoc](http://exploding-pixels.com/google_code/javadoc/com/explodingpixels/macwidgets/IAppWidgetFactory.html)) ###
iApp style scroll bars can be seen in various Apple applications, most notably [iTunes](http://www.apple.com/itunes).

![http://exploding-pixels.com/google_code/graphics/iAppScrollBars.png](http://exploding-pixels.com/google_code/graphics/iAppScrollBars.png)

**Using iApp scroll bars with a JScrollPane**
```
JScrollPane scrollPane = new JScrollPane(someComponent);
IAppWidgetFactory.makeIAppScrollPane(scrollPane);
```
**Using iApp scroll bars with a SourceList**
```
sourceList.useIAppStyleScrollBars();
```

### Heads Up Display (HUD) ([Javadoc](http://exploding-pixels.com/google_code/javadoc/com/explodingpixels/macwidgets/HudWindow.html)) ###
HUDs, also know as Transparent Panels, (descibed [here](http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_6.html#//apple_ref/doc/uid/20000961-SW24) in the Apple Human Interface Guidelines) provide a way to unobtrusively display transient information.

![http://exploding-pixels.com/google_code/graphics/HeadsUpDisplay.png](http://exploding-pixels.com/google_code/graphics/HeadsUpDisplay.png)

**Simple HUD example:**
```
HudWindow hud = new HudWindow("Window");
hud.getJDialog().setSize(300, 350);
hud.getJDialog().setLocationRelativeTo(null);
hud.getJDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
hud.getJDialog().setVisible(true);
```

### HUD Style Controls ([Javadoc](http://exploding-pixels.com/google_code/javadoc_0.9.4/com/explodingpixels/macwidgets/HudWidgetFactory.html)) ###
HUD style controls should be used in conjunction with HUD windows.

![http://exploding-pixels.com/google_code/graphics/HUDLabelUI.png](http://exploding-pixels.com/google_code/graphics/HUDLabelUI.png)

```
JLabel label = HudWidgetFactory.createHudLabel("Label");
```

![http://exploding-pixels.com/google_code/graphics/HUDButtonUI.png](http://exploding-pixels.com/google_code/graphics/HUDButtonUI.png)

```
JButton button = HudWidgetFactory.createHudButton("Button");
```

![http://exploding-pixels.com/google_code/graphics/HUDCheckBoxUI.png](http://exploding-pixels.com/google_code/graphics/HUDCheckBoxUI.png)

```
JCheckBox checkBox = HudWidgetFactory.createHudCheckBox("Check Box");
```

![http://exploding-pixels.com/google_code/graphics/HUDCobmoBoxUI.png](http://exploding-pixels.com/google_code/graphics/HUDCobmoBoxUI.png)

```
JComboBox comboBox = HudWidgetFactory.createHudComboBox(
        new DefaultComboBoxModel(new String[]{"Item One", "Item Two", "Item Three"}));
```

![http://exploding-pixels.com/google_code/graphics/HUDTextFieldUI.png](http://exploding-pixels.com/google_code/graphics/HUDTextFieldUI.png)

```
JTextField textField = HudWidgetFactory.createHudTextField("Text field");
```

### Source Lists ([Javadoc](http://exploding-pixels.com/google_code/javadoc/index.html?com/explodingpixels/macwidgets/SourceList.html)) ###
Source Lists (described [here](http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-CHDDIGDE) in the Apple Human Interface Guidelines) are a way to navigate or select objects in an application. There are two types of Source Lists: focusable and non-focusable. Focusable Source Lists can receive keyboard focus, and thus be navigated with the arrow keys, whereas non-focusable Source Lists cannot be navigated with the keyboard. Here is a focusable and non-focusable Source List:

![http://exploding-pixels.com/google_code/graphics/iTunesSourceList.png](http://exploding-pixels.com/google_code/graphics/iTunesSourceList.png) ![http://exploding-pixels.com/google_code/graphics/MailSourceListWithControlBar-selection.png](http://exploding-pixels.com/google_code/graphics/MailSourceListWithControlBar-selection.png)

**Simple Source List example:**
```
SourceListModel model = new SourceListModel();
SourceListCategory category = new SourceListCategory("Category");
model.addCategory(category);
model.addItemToCategory(new SourceListItem("Item"), category);
SourceList sourceList = new SourceList(model);
```

### Dark Source Lists ([Javadoc](http://exploding-pixels.com/google_code/javadoc_0.9.4/com/explodingpixels/macwidgets/SourceListDarkColorScheme.html)) ###

The `SourceListDarkColorScheme` can be installed on `SourceList`s used in applications where focus on content is critical, like photo editing applications.

![http://exploding-pixels.com/google_code/graphics/iMovieSourceList.png](http://exploding-pixels.com/google_code/graphics/iMovieSourceList.png)

```
SourceListModel model = new SourceListModel();
SourceListCategory category = new SourceListCategory("Category");
model.addCategory(category);
model.addItemToCategory(new SourceListItem("Item"), category);
SourceList sourceList = new SourceList(model);
sourceList.setColorScheme(new SourceListDarkColorScheme());
```

### Bottom Bar ([Javadoc](http://exploding-pixels.com/google_code/javadoc/com/explodingpixels/macwidgets/BottomBar.html)) ###
Bottom Bars (described [here](http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-SW6) in the Apple Human Interface Guidelines) provide a mechanism to add controls to an application that affect the contents or organization of the window contents.

![http://exploding-pixels.com/google_code/graphics/BottomBar.png](http://exploding-pixels.com/google_code/graphics/BottomBar.png)

**Simple Bottom Bar example:**
```
BottomBar bottomBar = BottomBar(BottomBarSize.LARGE);
bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel("My Label"));
```