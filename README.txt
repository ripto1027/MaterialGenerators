========================================
Material Generators
========================================

[English]

Material Generators is a Minecraft mod that introduces a configurable item generation system, allowing you to automate the production of various materials and items over time.

Features
--------
- Automatic Item Generation
  Set an item in the Generator block to automatically generate that item over time.

- Customizable Generation Targets
  Configure which items can be generated through the server configuration file. Both individual item IDs and item tags are supported, allowing compatibility with items from other mods.

- Progressive Upgrades
  Generators gradually become more efficient as they operate, increasing the number of items generated per cycle and reducing the cooldown time.

- Upgrade Cards
  Use special cards to enhance your Generators. C Cards reduce the cooldown time, while I Cards increase the number of items generated per cycle.

- Automatic Item Transfer
  Generators can automatically transfer their output to inventories above or below them.

- Jade Integration
  View the generation count, cooldown time, and generation progress directly in-game.

- Persistent Configuration
  Generator settings are preserved when the block is broken and picked up, allowing you to relocate it without losing its configuration.

How to Use the Generator
------------------------
- Place the Generator. Place a Generator block in the world.

- Set a Generation Item. Hold an item that is allowed by the server configuration and right-click the Generator to set it as the generation target. One item will be consumed.

- Wait for Generation. The Generator will periodically produce the configured item. The generation interval decreases over time, and the output per cycle gradually increases.

- Collect the Output. The generated items can be extracted automatically by inventories placed above or below the Generator. You can also crouch and right-click the Generator with an empty hand to retrieve the configured item and any stored output.

- Upgrade the Generator. Right-click the Generator while holding an upgrade card to apply its effect:

    - C Card: Reduces the remaining cooldown. Each card item reduces it by 20 ticks.

    - I Card: Increases the number of items generated per cycle.

- Check Its Status. If Jade is installed, you can view the Generator's output count, cooldown, and generation progress in-game.

Note
----
- Upgrade cards can also be obtained as rare drops from mobs killed by players. Higher Looting levels increase their drop chances.

Build your own automated production system and customize it to suit your needs!


========================================
Material Generators
========================================

[日本語]

Material Generators は、時間経過によってさまざまな素材やアイテムを自動生成できる、カスタマイズ可能なアイテム生成システムを追加する Minecraft モッドです。

主な機能
-------
- アイテムの自動生成
  Generator ブロックに生成したいアイテムを設定すると、時間経過に応じてそのアイテムを自動生成します。

- 生成対象のカスタマイズ
  サーバー設定ファイルから生成可能なアイテムを変更できます。個別のアイテム ID とアイテムタグの両方に対応しており、他のモッドのアイテムも生成対象にできます。

- 段階的な性能向上
  Generator は稼働を続けることで徐々に効率が向上し、1 サイクルあたりの生成数が増加するとともに、生成間隔が短縮されます。

- アップグレードカード
  専用のカードを使用して Generator を強化できます。C カードは生成間隔を短縮し、I カードは1サイクルあたりの生成数を増加させます。

- アイテムの自動搬出
  生成したアイテムを、上または下に接続されたインベントリへ自動的に搬出できます。

- Jade との連携
  ゲーム内で Generator の生成数、クールダウン時間、生成の進行状況を確認できます。

- 設定の保持
  ブロックを破壊して回収しても Generator の設定は保持されるため、設定を失うことなく別の場所へ移設できます。

Generatorの使い方
----------------
- Generator を設置する： Generator ブロックをワールドに設置します。

- 生成するアイテムを設定する： サーバー設定で生成が許可されているアイテムを手に持ち、Generator を右クリックします。設定時にアイテムが1個消費されます。

- 生成を待つ： Generator は一定時間ごとに設定したアイテムを生成します。稼働を続けると生成間隔が短くなり、1サイクルあたりの生成数も徐々に増加します。

- 生成物を回収する： Generator の上または下にインベントリを設置すると、生成物を自動搬出できます。また、何も持たずにスニークしながら右クリックすると、設定したアイテムと内部に保管されている生成物を回収できます。

- Generator を強化する： カードを手に持って Generator を右クリックすると、カードの効果を適用できます。

    - C カード： 残りのクールダウン時間を短縮します。カード1個につき20ティック短縮します。

    - I カード： 1サイクルあたりの生成数を増加させます。

- 稼働状況を確認する： Jade を導入している場合、Generator の生成数、クールダウン時間、生成の進行状況をゲーム内で確認できます。

補足
----
アップグレードカードは、プレイヤーが倒したモブから低確率でドロップします。ドロップ増加（Looting）のレベルが高いほど、ドロップ率も上昇します。

自分だけの自動生産システムを構築し、用途に合わせてカスタマイズしましょう！