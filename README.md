![](./assets/ManagedMenus.png)
# ManagedMenus

ManagedMenus is a lightweight library used to create and handle custom GUIs easily in Spigot and Paper plugins.

## Installation

### Maven

1. Add this repository:

````xml
<repository>
    <id>efekosdev</id>
    <url>https://efekos.dev/maven</url>
</repository>
````

2. Add this dependency. Use the latest release as the version. Replace `mm-paper` with `mm-spigot` if you use Spigot.

````xml
<dependency>
    <dependency>
        <groupId>dev.efekos.mm</groupId>
        <artifactId>mm-paper</artifactId>
        <version>1.0</version>
    </dependency>
</dependency>
````

### Gradle

1. Add this repository:

```gradle
maven { url = 'https://efekos.dev/maven' }
```

2. Add this dependency. Use the latest release as the version. Replace `mm-paper` with `mm-spigot` if you use Spigot.

```gralde
implementation 'dev.efekos.mm:mm-paper:1.0'
```

## Usage

First off, you need to register **MenuEvents** in your `onEnable` method. This event listener will listen to a few menu
events.

````java
import dev.efekos.mm.MenuEvents;

public void onEnable(){
    getServer().getPluginManager().registerEvents(new MenuEvents(),this);
}
````

Create an implementation of **Menu** or **PaginatedMenu** at anywhere you would like to. Here is a full example
(using `mm-spigot`)

````java
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class MyMenu extends Menu {

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 6; // 6*9 = 54 slots
    }

    @Override
    public String getTitle() {
        return "My New Menu";
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        owner.sendMessage("You clicked slot "+e.getSlot()+"!");
        if(isButton(e.getCurrentItem(),"diamond_button")){
            owner.getInventory().addItem(new ItemStack(Material.DIAMOND));
            owner.sendMessage("Added a diamond to your inventory!");
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        owner.sendMessage("You closed the menu!");
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        owner.sendMessage("You opened the menu!");
    }

    @Override
    public void fill() {
        inventory.setItem(0,createItem(Material.IRON_SWORD, ChatColor.RED+"Blood Sword",ChatColor.DARK_RED+"This sword is covered in blood."));
        inventory.setItem(1,createSkull(this.owner,ChatColor.YELLOW+owner.getName(),ChatColor.GOLD+"This is you!"));
        inventory.setItem(2,createButton("diamond_button",Material.DIAMOND,ChatColor.AQUA+"Diamond giver",
                ChatColor.AQUA+"Click this button to",
                ChatColor.AQUA+"get a diamond!"
        ));

        fillEmptyWith(createBlackStainedGlassPane());
    }

}
````

In anywhere of the code, construct your own menu and call `open()` to open the menu.

```java
import dev.efekos.mm.MenuContext;

@EventHandler
public void onJoin(PlayerJoinEvent e){
    new MyMenu(MenuContext.of(e.getPlayer())).open();
}

```

You can use **MenuContext** class to store global data across different GUIs.

# License

This library is licensed with the [MIT License](./LICENSE.md).