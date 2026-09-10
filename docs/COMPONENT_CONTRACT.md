# Component Contract

Planet Components should keep naming, props, variants, and visual semantics aligned across platforms.

## Shared Theme

Theme name: `star_planet`

Core semantic colors:

- `pageStart`
- `pageEnd`
- `textPrimary`
- `textSecondary`
- `textTertiary`
- `surfaceRaised`
- `borderDefault`
- `brandPrimary`
- `brandDark`
- `success`
- `warning`
- `danger`
- `selectedFill`
- `activeFill`

## Component Set

| Component | Required variants | Core props |
| --- | --- | --- |
| Button | `primary`, `default`, `danger`, `text`, `link` | `text`, `variant`, `disabled`, `fullWidth`, `loading`, `onTap` |
| Card | `default`, `subtle`, `selected`, `disabled` | `variant`, `selected`, `disabled`, `content` |
| Alert | `info`, `success`, `warning`, `error` | `title`, `message`, `variant` |
| Badge | `default`, `primary`, `success`, `warning`, `danger` | `text`, `variant`, `disabled` |
| Chip | `default`, `primary`, `success`, `warning`, `danger` | `text`, `variant`, `selected`, `disabled`, `onTap` |
| Input | `default`, `error`, `disabled` | `value`, `placeholder`, `variant`, `disabled`, `onChange` |
| Select | `default`, `disabled` | `options`, `selectedIndex`, `disabled`, `onSelect` |
| OptionSheet | `default`, `mobile` | `title`, `options`, `selectedIndex`, `visible`, `onSelect`, `onCancel` |
| Switch | `md`, `sm`, `loading`, `disabled` | `text`, `checked`, `checkedText`, `uncheckedText`, `loading`, `disabled`, `variant`, `onChange`（**扁平常规 UI**，见 `docs/prds/cross-platform-ui-parity/specs/05-switch-contract.md`；禁止系统 Switch / 岛屿轨内字） |
| Progress | `primary`, `warning`, `success`, `danger` | `progress`, `variant` |
| TopBar | `default` | `title`, `showBack`, `backgroundColor`, `onBack` |
| BottomTab | `default` | `tabs`, `selectedKey`, `onSelect` |
| Tabs | `default` | `tabs`, `selectedIndex`, `onSelect` |
| Amount | `symbolBefore`, `symbolAfter`, `strikeThrough` | `symbol`, `value`, `cycle`, `symbolAfter`, `strikeThrough` |
| IconButton | `default`, `primary`, `selected`, `disabled` | `icon`, `selected`, `disabled`, `onTap` |
| KeyValueLabel | `default` | `label`, `value` |
| Notification | `info`, `alert` | `title`, `message`, `variant` |
| TextLink | `default`, `inverse` | `text`, `inverse`, `onTap` |
| Stepper | `3steps`, `4steps`, `5steps` | `stepCount`, `currentStep` |
| StickyFooter | `default`, `subtle` | `content` |
| PinInput | `4cells`, `6cells`, `secure` | `value`, `cellCount`, `secure`, `onComplete` |
| ListItem | `default`, `selected`, `disabled` | `title`, `message`, `trailing`, `selected`, `disabled`, `onTap` |
| Empty | `default` | `title`, `message`, `actionText`, `onAction` |
| Toast | `info`, `success`, `warning`, `error` | `message`, `variant`, `duration` |
| Modal | `confirm` | `title`, `message`, `confirmText`, `cancelText`, `onConfirm`, `onCancel` |
| RefreshLayout | `refreshing`, `loadingMore`, `disabled` | `refreshing`, `loadingMore`, `onRefresh`, `onLoadMore` |
| LoadingDialog | `default`, `compact` | `visible`, `message`, `dismissible` |
| DatePicker | `default`, `disabled` | `value`, `onChange`, `min`, `max`, `placeholder`, `disabled` |
| ChildSwitcher | `chip`, `tabs` | `items`, `selectedId`, `onChange`, `disabled` |
| ScoreRuleGrid | `default`, `readOnly` | `rules`, `columns`, `onIncrement`, `disabled` |
| RedeemCardGrid | `default`, `frozen` | `items`, `availablePoints`, `frozen`, `onRedeem`, `disabled` |
| CalendarHeatmap | `month` | `yearMonth`, `cells`, `onSelectDay`, `showLegend` |
| PrintSheet | `pinyin`, `meaning` | `title`, `items`, `columns`, `footerFields` |
| BalanceHero | `default`, `compact` | `total`, `breakdown`, `suffix` |
| CheckInStreakCard | `default` | `streakDays`, `totalDays`, `weekProgress`, `onOpen`, `disabled` |
| Checkbox | `default`, `disabled` | `text`, `variant`, `checked`, `disabled`, `onChange` |
| Collapse | `default`, `disabled` | `title`, `message`, `expanded`, `disabled`, `onChange` |
| Divider | `default`, `disabled` | `variant`, `text`, `disabled` |
| Radio | `default`, `disabled` | `text`, `checked`, `disabled`, `onChange` |
| SearchBar | `default`, `disabled` | `value`, `placeholder`, `variant`, `disabled`, `onChange` |
| SegmentedControl | `default`, `disabled` | `options`, `selectedIndex`, `disabled`, `onSelect` |
| StarRating | `default`, `disabled`, `readonly` | `value`, `max`, `disabled`, `onChange` |
| Avatar | `default`, `primary`, `subtle` | `text`, `src`, `size`, `variant` |
| Skeleton | `default`, `pulse` | `rows`, `animated`, `avatar` |
| Tooltip | `default` | `text`, `placement`, `visible`, `children` |
| Slider | `default`, `disabled` | `value`, `min`, `max`, `step`, `disabled`, `onChange` |
| TextArea | `default`, `error`, `disabled` | `value`, `placeholder`, `rows`, `maxLength`, `disabled`, `onChange` |
| Drawer | `default` | `visible`, `title`, `placement`, `onClose`, `children` |
| InputNumber | `default`, `disabled` | `value`, `min`, `max`, `step`, `disabled`, `onChange` |
| Swiper | `default` | `items`, `index`, `autoplay`, `onChange` |
| Tag | `default`, `primary`, `success`, `warning`, `danger` | `text`, `closable`, `selected`, `disabled`, `variant`, `onClose`, `onTap` |
| Fab | `primary`, `default` | `icon`, `text`, `variant`, `disabled`, `onTap` |
| TimePicker | `default`, `disabled` | `value`, `placeholder`, `disabled`, `onChange` |
| Upload | `default`, `disabled` | `files`, `multiple`, `disabled`, `accept`, `onChange`, `onRemove` |
| Table | `default`, `striped` | `columns`, `rows`, `variant`, `emptyText` |
| Tree | `default` | `items`, `selectedId`, `expandedIds`, `onSelect`, `onExpand` |
| Cascader | `default`, `disabled` | `options`, `value`, `placeholder`, `disabled`, `onChange` |

## Platform public names

| Contract | RN / Web / Flutter / iOS | Android View | WeChat Mini Program tag |
| --- | --- | --- | --- |
| Button | `TspButton` | `BasicButton` | `bc-button` |
| Card | `TspCard` | `BasicCardView` | `bc-card` |
| Alert | `TspAlert` | `BasicAlertView` | `bc-alert` |
| Badge | `TspBadge` | `BasicBadgeView` | `bc-badge` |
| Chip | `TspChip` | `BasicChipView` | `bc-chip` |
| Input | `TspInput` | `BasicInputView` | `bc-input` |
| Select | `TspSelect` | `BasicSelectView` | `bc-select` |
| OptionSheet | `TspOptionSheet` | `BasicOptionSheet` | `bc-option-sheet` |
| Switch | `TspSwitch` | `BasicSwitchView` | `bc-switch` |
| Progress | `TspProgress` | `BasicProgressView` | `bc-progress` |
| TopBar | `TspTopBar` | `BasicTopBarView` | `bc-top-bar` |
| BottomTab | `TspBottomTab` | `BasicBottomTabView` | `bc-bottom-tab` |
| Tabs | `TspTabs` | `BasicTabsView` | `bc-tabs` |
| Amount | `TspAmount` | `BasicAmountView` | `bc-amount` |
| IconButton | `TspIconButton` | `BasicIconButtonView` | `bc-icon-button` |
| KeyValueLabel | `TspKeyValueLabel` | `BasicKeyValueLabelView` | `bc-key-value-label` |
| Notification | `TspNotification` | `BasicNotificationView` | `bc-notification` |
| TextLink | `TspTextLink` | `BasicTextLinkView` | `bc-text-link` |
| Stepper | `TspStepper` | `BasicStepperView` | `bc-stepper` |
| StickyFooter | `TspStickyFooter` | `BasicStickyFooterView` | `bc-sticky-footer` |
| PinInput | `TspPinInput` | `BasicPinInputView` | `bc-pin-input` |
| ListItem | `TspListItem` | `BasicListItemView` | `bc-list-item` |
| Empty | `TspEmpty` | `BasicEmptyView` | `bc-empty` |
| Toast | `TspToast` | `BasicToast` | `bc-toast` |
| Modal | `TspModal` | `BasicModalDialog` | `bc-modal` |
| RefreshLayout | `TspRefreshLayout` | `BasicRefreshLayout` | `bc-refresh-layout` |
| LoadingDialog | `TspLoadingDialog` | `BasicLoadingDialog` | `bc-loading-dialog` |
| DatePicker | `TspDatePicker` | `BasicDatePickerView` | `bc-date-picker` |
| ChildSwitcher | `TspChildSwitcher` | `BasicChildSwitcherView` | `bc-child-switcher` |
| ScoreRuleGrid | `TspScoreRuleGrid` | `BasicScoreRuleGridView` | `bc-score-rule-grid` |
| RedeemCardGrid | `TspRedeemCardGrid` | `BasicRedeemCardGridView` | `bc-redeem-card-grid` |
| CalendarHeatmap | `TspCalendarHeatmap` | `BasicCalendarHeatmapView` | `bc-calendar-heatmap` |
| PrintSheet | `TspPrintSheet` | `BasicPrintSheetView` | `bc-print-sheet` |
| BalanceHero | `TspBalanceHero` | `BasicBalanceHeroView` | `bc-balance-hero` |
| CheckInStreakCard | `TspCheckInStreakCard` | `BasicCheckInStreakCardView` | `bc-check-in-streak-card` |
| Checkbox | `TspCheckbox` | `BasicCheckboxView` | `bc-checkbox` |
| Collapse | `TspCollapse` | `BasicCollapseView` | `bc-collapse` |
| Divider | `TspDivider` | `BasicDividerView` | `bc-divider` |
| Radio | `TspRadio` | `BasicRadioView` | `bc-radio` |
| SearchBar | `TspSearchBar` | `BasicSearchBarView` | `bc-search-bar` |
| SegmentedControl | `TspSegmentedControl` | `BasicSegmentedControl` | `bc-segmented-control` |
| StarRating | `TspStarRating` | `BasicStarRatingView` | `bc-star-rating` |
| Avatar | `TspAvatar` | `BasicAvatarView` | `bc-avatar` |
| Skeleton | `TspSkeleton` | `BasicSkeletonView` | `bc-skeleton` |
| Tooltip | `TspTooltip` | `BasicTooltipView` | `bc-tooltip` |
| Slider | `TspSlider` | `BasicSliderView` | `bc-slider` |
| TextArea | `TspTextArea` | `BasicTextAreaView` | `bc-text-area` |
| Drawer | `TspDrawer` | `BasicDrawerView` | `bc-drawer` |
| InputNumber | `TspInputNumber` | `BasicInputNumberView` | `bc-input-number` |
| Swiper | `TspSwiper` | `BasicSwiperView` | `bc-swiper` |
| Tag | `TspTag` | `BasicTagView` | `bc-tag` |
| Fab | `TspFab` | `BasicFabView` | `bc-fab` |
| TimePicker | `TspTimePicker` | `BasicTimePickerView` | `bc-time-picker` |
| Upload | `TspUpload` | `BasicUploadView` | `bc-upload` |
| Table | `TspTable` | `BasicTableView` | `bc-table` |
| Tree | `TspTree` | `BasicTreeView` | `bc-tree` |
| Cascader | `TspCascader` | `BasicCascaderView` | `bc-cascader` |

Mini Program keeps `bc-*` tags because WeChat `usingComponents` is path/tag based. Semantic APIs still use `variant` / `disabled` / `text` / `theme`. Flutter Button uses enum value `standard` for contract `default` because `default` is reserved in Dart.

Kuikly public names match RN / Web / Flutter / iOS (`Tsp*`). The Kuikly tree still hosts phonics sample pages beside the control files.

## Sample Coverage

Every component added to a library must appear in that stack's `samples` project with:

- default state
- disabled state where supported
- selected/checked/focused state where supported
- loading/error/success/warning state where supported
- a realistic usage scenario, not only an isolated rendering

