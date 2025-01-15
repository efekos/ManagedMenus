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

package dev.efekos.mm.task;

import dev.efekos.mm.Menu;
import dev.efekos.mm.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuTickTask extends BukkitRunnable {

    private final Menu menu;
    private final Player opener;

    public MenuTickTask(Menu menu, Player opener) {
        this.menu = menu;
        this.opener = opener;
    }

    @Override
    public void run() {
        for (MenuItem item:menu.getItems()) if(item.isTicked()) item.tick(menu.getInventory(),opener);
    }

}
