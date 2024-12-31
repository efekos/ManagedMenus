/*
 * Copyright (c) 2024 efekos
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

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu {
    protected static final int maxItemsPerPage = 28;
    private int page = 0;
    private List<ItemStack> items = new ArrayList<>();

    public PaginatedMenu(MenuContext data) {
        super(data);
    }

    protected abstract List<ItemStack> getItems();

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 48 -> {
                if (page != 0) {
                    page--;
                    refresh();
                }
            }
            case 50 -> {
                int maxPages = (int) Math.ceil((double) items.size() / maxItemsPerPage) - 1;
                if (page < maxPages) {
                    page++;
                    e.setCancelled(true);
                    refresh();
                }
            }
        }
    }

    @Override
    public void fill() {
        this.items = getItems();
        for (Integer i : new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 51, 52, 53}) {
            inventory.setItem(i, createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
        }

        inventory.setItem(48, createItem(Material.PAPER, getPreviousPageText()));
        inventory.setItem(49, createItem(Material.BOOK, getCurrentPageText(page + 1, Math.round((float) items.size() / maxItemsPerPage) + 1)));
        inventory.setItem(50, createItem(Material.PAPER, getNextPageText()));

        int index = page * maxItemsPerPage;

        try {
            for (int i = 0; i <= 27; i++) {
                if (items.size() > i) inventory.addItem(items.get(index + i));
            }
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) return;
            e.printStackTrace();
        }
    }

    protected abstract String getNextPageText();

    protected abstract String getCurrentPageText(int page, int maxPage);

    protected abstract String getPreviousPageText();

}