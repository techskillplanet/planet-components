import Foundation

/// Sample-only i18n helper aligned with RN/Android `sample_strings.json`.
struct SampleI18n {
    private let tables: [String: [String: String]]
    private(set) var language: String

    private static let supported = ["zh-CN", "en", "ja"]

    init(language: String = "zh-CN") {
        self.tables = Self.loadTables()
        self.language = Self.supported.contains(language) ? language : "zh-CN"
    }

    mutating func setLanguage(_ next: String) {
        guard tables[next] != nil else { return }
        language = next
    }

    func t(_ key: String, _ args: String...) -> String {
        let raw = tables[language]?[key]
            ?? tables["zh-CN"]?[key]
            ?? Self.fallback[language]?[key]
            ?? Self.fallback["zh-CN"]?[key]
            ?? key
        guard !args.isEmpty else { return raw }
        var index = 0
        var result = ""
        var remaining = raw[...]
        while let range = remaining.range(of: "%s") {
            result += remaining[..<range.lowerBound]
            result += index < args.count ? args[index] : ""
            index += 1
            remaining = remaining[range.upperBound...]
        }
        result += remaining
        return result
    }

    private static func loadTables() -> [String: [String: String]] {
        let candidates: [URL?] = [
            Bundle.module.url(forResource: "sample_strings", withExtension: "json"),
            Bundle.main.url(forResource: "sample_strings", withExtension: "json")
        ]
        guard let url = candidates.compactMap({ $0 }).first,
              let data = try? Data(contentsOf: url),
              let json = try? JSONSerialization.jsonObject(with: data) as? [String: [String: String]]
        else {
            return [:]
        }
        return json
    }

    /// A few chrome strings used by the iOS sample that are not in the shared JSON yet.
    private static let fallback: [String: [String: String]] = [
        "zh-CN": [
            "sample/common/confirm": "确定",
            "sample/common/cancel": "取消",
            "sample/toast/saved": "已保存",
            "sample/demo/list/selected": "选中状态",
            "sample/demo/list/disabled_title": "不可点击",
            "sample/demo/list/disabled_body": "禁用状态",
            "sample/demo/refresh/row": "下拉刷新 / 上拉加载",
            "sample/demo/switch/sm": "小号",
            "sample/demo/switch/loading_short": "加载中",
            "sample/demo/switch/disabled_short": "禁用",
            "sample/demo/tabs/all": "全部",
            "sample/demo/tabs/learned": "已学",
            "sample/demo/tabs/todo": "未学"
        ],
        "en": [
            "sample/common/confirm": "Confirm",
            "sample/common/cancel": "Cancel",
            "sample/toast/saved": "Saved",
            "sample/demo/list/selected": "Selected",
            "sample/demo/list/disabled_title": "Disabled",
            "sample/demo/list/disabled_body": "Disabled state",
            "sample/demo/refresh/row": "Pull to refresh / load more",
            "sample/demo/switch/sm": "Small",
            "sample/demo/switch/loading_short": "Loading",
            "sample/demo/switch/disabled_short": "Disabled",
            "sample/demo/tabs/all": "All",
            "sample/demo/tabs/learned": "Learned",
            "sample/demo/tabs/todo": "Todo"
        ],
        "ja": [
            "sample/common/confirm": "確認",
            "sample/common/cancel": "キャンセル",
            "sample/toast/saved": "保存しました",
            "sample/demo/list/selected": "選択中",
            "sample/demo/list/disabled_title": "無効",
            "sample/demo/list/disabled_body": "無効状態",
            "sample/demo/refresh/row": "引っ張って更新 / 追加読み込み",
            "sample/demo/switch/sm": "小型",
            "sample/demo/switch/loading_short": "読み込み中",
            "sample/demo/switch/disabled_short": "無効",
            "sample/demo/tabs/all": "すべて",
            "sample/demo/tabs/learned": "学習済",
            "sample/demo/tabs/todo": "未学習"
        ]
    ]
}
