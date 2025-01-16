![](./assets/ManagedMenus.png)
# ManagedMenus

![](https://badgen.net/github/license/efekos/ManagedMenus)
![](https://badgen.net/github/stars/efekos/ManagedMenus)
![](https://badgen.net/github/release/efekos/ManagedMenus)
![](https://badgen.net/github/releases/efekos/ManagedMenus)
![](https://badgen.net/github/merged-prs/efekos/ManagedMenus)
![](https://badgen.net/github/issues/efekos/ManagedMenus)

<!-- TOC -->
* [ManagedMenus](#managedmenus)
* [Installation](#installation)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Usage](#usage)
* [License](#license)
<!-- TOC -->

ManagedMenus is a lightweight library used to create and handle custom GUIs easily in Spigot and Paper plugins.

# Installation

## Maven

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

## Gradle

1. Add this repository:

```gradle
maven { url = 'https://efekos.dev/maven' }
```

2. Add this dependency. Use the latest release as the version. Replace `mm-paper` with `mm-spigot` if you use Spigot.

```gralde
implementation 'dev.efekos.mm:mm-paper:1.0'
```

# Usage

Here is a quick example to get started.

````java
Player player;
JavaPlugin instance; // Your plugin instance is required when creating a menu
Advancement diamonds = Bukkit.getAdvancement(new NamespacedKey("minecraft","story/mine_diamond"));
Advancement enchanter = Bukkit.getAdvancement(new NamespacedKey("minecraft","story/enchant_item"));
Menu menu = new Menu(Component.text("Vault"), instance, 6 /*rows, so a large chest*/)
        .addItem(MenuItem.background(BackgroundColor.BLACK))
        .addItem(MenuItem.skull(0,player))
        .addItem(MenuItem.button(new ItemStack(Material.IRON_AXE,1))
                .slot(1) // you don't have to define slots like this every time. There are many other static methods in MenuItem.
                .onClick((slot, clickedItem, clicker, inventory) -> clicker.damage(20))
        )
        .addItem(MenuItem.dynamicStack(new ItemStack(Material.IRON_PICKAXE,1)) // The difference between normal buttons/stacks and dynamic buttons/stacks
                .slot(2)                                                       // is that dynamic stacks refresh their data in every tick
                .dynamicMaterial((inventory, p) -> p.getAdvancementProgress(diamonds).isDone()?Material.DIAMOND_PICKAXE:Material.IRON_PICKAXE)
                .dynamicGlint((inventory, p) -> p.getAdvancementProgress(enchanter).isDone()) // Uses mending to add glint and hides enchantments
                .dynamicName((inventory, p) -> Component.text(p.getName()).append(Component.text("'s pickaxe")))
                .refreshRate(2) // You can adjust the refresh rate if refreshing every tick is too much for the data used in the item
        )
        .addItem(MenuItem.square(BackgroundColor.BLUE.item())
                .position(1,1)
                .size(7,4)
        )
        .addItem(MenuItem.clickListener(9,10,11,12,13,14,15,16) // does not add any item, only to listen to clicks in specific slots.
                .onClick((slot, clickedItem, clicker, inventory) -> clicker.sendMessage(Component.text("You clicked the second row!")))
        )
        .addItem(MenuItem.ticker((inventory, player1) -> System.out.println("debug log: "+player1.getName()+" : "+inventory.getSize()))) // just a ticker, useful for counters.
        .onOpen(inventoryOpenEvent -> inventoryOpenEvent.getPlayer().sendMessage(Component.text("You opened the inventory!")))
        .onClose(inventoryCloseEvent -> inventoryCloseEvent.getPlayer().sendMessage(Component.text("You closed the inventory!")))
        ;
        menu.open(player);
````

# License

This library is licensed with the [MIT License](./LICENSE.md).