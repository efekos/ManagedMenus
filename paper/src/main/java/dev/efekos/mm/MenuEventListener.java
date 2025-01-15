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

package dev.efekos.mm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class MenuEventListener implements Listener {

    private final Menu menu;

    public MenuEventListener(Menu menu) {
        this.menu = menu;
    }

    @EventHandler
    public void onMenuOpen(InventoryOpenEvent e){
        if (e.getInventory().getHolder()==menu) menu.onOpen(e);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e){
        if (e.getInventory().getHolder()==menu) menu.onClose(e);
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent e){
        if(e.getInventory().getHolder()==menu) menu.onDrag(e);
    }

    @EventHandler
    public void onMenuMove(InventoryMoveItemEvent e){
        if(e.getDestination().getHolder()==menu||e.getSource().getHolder()==menu) menu.onMove(e);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        if(e.getInventory().getHolder()==menu) menu.onClick(e);
    }

}