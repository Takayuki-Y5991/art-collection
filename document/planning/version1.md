## 要件

1. アプリケーションをアップロードすることができる

   - 作品名, クリエータめい, タグ, 説明, 表示非表示, AI 学習 OK タグを持つ
   - 作品の投稿は 1 日 - 5 までとする
   - アップロードサイズ - 32MB
   - タグは複数件設定可能
   - 表示オプション - display, no display
   - AI 学習タグ - True の場合は、そのままの画像を表示、それ以外の場合は、Backend 側で画像をいじって表示する

2. 一覧表示機能

   - 作品名, タグ, クリエータ名による検索が可能
     - この一覧表示では、表示が非表示になっているものは表示できない。
     - 表示順番は基本的には登録順とする。
     - 1 ページの検索件数は 100 件とする
     - paging 機能により制御