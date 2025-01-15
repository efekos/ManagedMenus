/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.mm.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum BackgroundColor {
    BLACK(Material.BLACK_STAINED_GLASS_PANE),
    RED(Material.RED_STAINED_GLASS_PANE),
    GREEN(Material.GREEN_STAINED_GLASS_PANE),
    BLUE(Material.BLUE_STAINED_GLASS_PANE),
    PINK(Material.PINK_STAINED_GLASS_PANE),
    YELLOW(Material.YELLOW_STAINED_GLASS_PANE),
    ORANGE(Material.ORANGE_STAINED_GLASS_PANE),
    WHITE(Material.WHITE_STAINED_GLASS_PANE),
    GRAY(Material.GRAY_STAINED_GLASS_PANE),
    LIGHT_GRAY(Material.LIGHT_GRAY_STAINED_GLASS_PANE),
    CYAN(Material.CYAN_STAINED_GLASS_PANE),
    PURPLE(Material.PURPLE_STAINED_GLASS_PANE),
    MAGENTA(Material.MAGENTA_STAINED_GLASS_PANE),
    LIGHT_BLUE(Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    BROWN(Material.BROWN_STAINED_GLASS_PANE),
    LIME(Material.LIME_STAINED_GLASS_PANE)
    ;
    private final Material material;

    BackgroundColor(Material material) {
        this.material = material;
    }

    public ItemStack item() {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.empty());
        meta.addItemFlags(ItemFlag.values());
        stack.setItemMeta(meta);
        return stack;
    }

}
