# API Reference (contract-driven)

Generated from `component_contract.json` (57 components). Do not hand-edit; run `node tools/build-api-docs.cjs`.

## Theme tokens

`pageStart`, `pageEnd`, `textPrimary`, `textSecondary`, `textTertiary`, `surfaceRaised`, `borderDefault`, `brandPrimary`, `brandDark`, `success`, `warning`, `danger`, `selectedFill`, `activeFill`

## Naming

| Contract | Web / RN / Flutter / iOS / Kuikly | Android | Mini Program |
| --- | --- | --- | --- |
| Button | `TspButton` | `BasicButton` / `BasicButtonView` | `bc-button` |
| Card | `TspCard` | `BasicCard` / `BasicCardView` | `bc-card` |
| Alert | `TspAlert` | `BasicAlert` / `BasicAlertView` | `bc-alert` |
| Badge | `TspBadge` | `BasicBadge` / `BasicBadgeView` | `bc-badge` |
| Chip | `TspChip` | `BasicChip` / `BasicChipView` | `bc-chip` |
| Input | `TspInput` | `BasicInput` / `BasicInputView` | `bc-input` |
| Select | `TspSelect` | `BasicSelect` / `BasicSelectView` | `bc-select` |
| OptionSheet | `TspOptionSheet` | `BasicOptionSheet` / `BasicOptionSheetView` | `bc-option-sheet` |
| Switch | `TspSwitch` | `BasicSwitch` / `BasicSwitchView` | `bc-switch` |
| Progress | `TspProgress` | `BasicProgress` / `BasicProgressView` | `bc-progress` |
| TopBar | `TspTopBar` | `BasicTopBar` / `BasicTopBarView` | `bc-top-bar` |
| BottomTab | `TspBottomTab` | `BasicBottomTab` / `BasicBottomTabView` | `bc-bottom-tab` |
| Tabs | `TspTabs` | `BasicTabs` / `BasicTabsView` | `bc-tabs` |
| Amount | `TspAmount` | `BasicAmount` / `BasicAmountView` | `bc-amount` |
| IconButton | `TspIconButton` | `BasicIconButton` / `BasicIconButtonView` | `bc-icon-button` |
| KeyValueLabel | `TspKeyValueLabel` | `BasicKeyValueLabel` / `BasicKeyValueLabelView` | `bc-key-value-label` |
| Notification | `TspNotification` | `BasicNotification` / `BasicNotificationView` | `bc-notification` |
| TextLink | `TspTextLink` | `BasicTextLink` / `BasicTextLinkView` | `bc-text-link` |
| Stepper | `TspStepper` | `BasicStepper` / `BasicStepperView` | `bc-stepper` |
| StickyFooter | `TspStickyFooter` | `BasicStickyFooter` / `BasicStickyFooterView` | `bc-sticky-footer` |
| PinInput | `TspPinInput` | `BasicPinInput` / `BasicPinInputView` | `bc-pin-input` |
| ListItem | `TspListItem` | `BasicListItem` / `BasicListItemView` | `bc-list-item` |
| Empty | `TspEmpty` | `BasicEmpty` / `BasicEmptyView` | `bc-empty` |
| Toast | `TspToast` | `BasicToast` / `BasicToastView` | `bc-toast` |
| Modal | `TspModal` | `BasicModal` / `BasicModalView` | `bc-modal` |
| RefreshLayout | `TspRefreshLayout` | `BasicRefreshLayout` / `BasicRefreshLayoutView` | `bc-refresh-layout` |
| LoadingDialog | `TspLoadingDialog` | `BasicLoadingDialog` / `BasicLoadingDialogView` | `bc-loading-dialog` |
| DatePicker | `TspDatePicker` | `BasicDatePicker` / `BasicDatePickerView` | `bc-date-picker` |
| ChildSwitcher | `TspChildSwitcher` | `BasicChildSwitcher` / `BasicChildSwitcherView` | `bc-child-switcher` |
| ScoreRuleGrid | `TspScoreRuleGrid` | `BasicScoreRuleGrid` / `BasicScoreRuleGridView` | `bc-score-rule-grid` |
| RedeemCardGrid | `TspRedeemCardGrid` | `BasicRedeemCardGrid` / `BasicRedeemCardGridView` | `bc-redeem-card-grid` |
| CalendarHeatmap | `TspCalendarHeatmap` | `BasicCalendarHeatmap` / `BasicCalendarHeatmapView` | `bc-calendar-heatmap` |
| PrintSheet | `TspPrintSheet` | `BasicPrintSheet` / `BasicPrintSheetView` | `bc-print-sheet` |
| BalanceHero | `TspBalanceHero` | `BasicBalanceHero` / `BasicBalanceHeroView` | `bc-balance-hero` |
| CheckInStreakCard | `TspCheckInStreakCard` | `BasicCheckInStreakCard` / `BasicCheckInStreakCardView` | `bc-check-in-streak-card` |
| Checkbox | `TspCheckbox` | `BasicCheckbox` / `BasicCheckboxView` | `bc-checkbox` |
| Collapse | `TspCollapse` | `BasicCollapse` / `BasicCollapseView` | `bc-collapse` |
| Divider | `TspDivider` | `BasicDivider` / `BasicDividerView` | `bc-divider` |
| Radio | `TspRadio` | `BasicRadio` / `BasicRadioView` | `bc-radio` |
| SearchBar | `TspSearchBar` | `BasicSearchBar` / `BasicSearchBarView` | `bc-search-bar` |
| SegmentedControl | `TspSegmentedControl` | `BasicSegmentedControl` / `BasicSegmentedControlView` | `bc-segmented-control` |
| StarRating | `TspStarRating` | `BasicStarRating` / `BasicStarRatingView` | `bc-star-rating` |
| Avatar | `TspAvatar` | `BasicAvatar` / `BasicAvatarView` | `bc-avatar` |
| Skeleton | `TspSkeleton` | `BasicSkeleton` / `BasicSkeletonView` | `bc-skeleton` |
| Tooltip | `TspTooltip` | `BasicTooltip` / `BasicTooltipView` | `bc-tooltip` |
| Slider | `TspSlider` | `BasicSlider` / `BasicSliderView` | `bc-slider` |
| TextArea | `TspTextArea` | `BasicTextArea` / `BasicTextAreaView` | `bc-text-area` |
| Drawer | `TspDrawer` | `BasicDrawer` / `BasicDrawerView` | `bc-drawer` |
| InputNumber | `TspInputNumber` | `BasicInputNumber` / `BasicInputNumberView` | `bc-input-number` |
| Swiper | `TspSwiper` | `BasicSwiper` / `BasicSwiperView` | `bc-swiper` |
| Tag | `TspTag` | `BasicTag` / `BasicTagView` | `bc-tag` |
| Fab | `TspFab` | `BasicFab` / `BasicFabView` | `bc-fab` |
| TimePicker | `TspTimePicker` | `BasicTimePicker` / `BasicTimePickerView` | `bc-time-picker` |
| Upload | `TspUpload` | `BasicUpload` / `BasicUploadView` | `bc-upload` |
| Table | `TspTable` | `BasicTable` / `BasicTableView` | `bc-table` |
| Tree | `TspTree` | `BasicTree` / `BasicTreeView` | `bc-tree` |
| Cascader | `TspCascader` | `BasicCascader` / `BasicCascaderView` | `bc-cascader` |

## Components

### Button

- **Variants:** `primary`, `default`, `danger`, `text`, `link`
- **Props:** `text`, `variant`, `disabled`, `fullWidth`, `loading`, `onTap`

### Card

- **Variants:** `default`, `subtle`, `selected`, `disabled`
- **Props:** `variant`, `selected`, `disabled`, `content`

### Alert

- **Variants:** `info`, `success`, `warning`, `error`
- **Props:** `title`, `message`, `variant`

### Badge

- **Variants:** `default`, `primary`, `success`, `warning`, `danger`
- **Props:** `text`, `variant`, `disabled`

### Chip

- **Variants:** `default`, `primary`, `success`, `warning`, `danger`
- **Props:** `text`, `variant`, `selected`, `disabled`, `onTap`

### Input

- **Variants:** `default`, `error`, `disabled`
- **Props:** `value`, `placeholder`, `variant`, `disabled`, `onChange`

### Select

- **Variants:** `default`, `disabled`
- **Props:** `options`, `selectedIndex`, `disabled`, `onSelect`

### OptionSheet

- **Variants:** `default`, `mobile`
- **Props:** `title`, `options`, `selectedIndex`, `visible`, `onSelect`, `onCancel`

### Switch

- **Variants:** `md`, `sm`, `loading`, `disabled`
- **Props:** `text`, `checked`, `checkedText`, `uncheckedText`, `loading`, `disabled`, `onChange`

### Progress

- **Variants:** `primary`, `warning`, `success`, `danger`
- **Props:** `progress`, `variant`

### TopBar

- **Variants:** `default`
- **Props:** `title`, `showBack`, `backgroundColor`, `onBack`

### BottomTab

- **Variants:** `default`
- **Props:** `tabs`, `selectedKey`, `onSelect`

### Tabs

- **Variants:** `default`
- **Props:** `tabs`, `selectedIndex`, `onSelect`

### Amount

- **Variants:** `symbolBefore`, `symbolAfter`, `strikeThrough`
- **Props:** `symbol`, `value`, `cycle`, `symbolAfter`, `strikeThrough`

### IconButton

- **Variants:** `default`, `primary`, `selected`, `disabled`
- **Props:** `icon`, `selected`, `disabled`, `onTap`

### KeyValueLabel

- **Variants:** `default`
- **Props:** `label`, `value`

### Notification

- **Variants:** `info`, `alert`
- **Props:** `title`, `message`, `variant`

### TextLink

- **Variants:** `default`, `inverse`
- **Props:** `text`, `inverse`, `onTap`

### Stepper

- **Variants:** `3steps`, `4steps`, `5steps`
- **Props:** `stepCount`, `currentStep`

### StickyFooter

- **Variants:** `default`, `subtle`
- **Props:** `content`

### PinInput

- **Variants:** `4cells`, `6cells`, `secure`
- **Props:** `value`, `cellCount`, `secure`, `onComplete`

### ListItem

- **Variants:** `default`, `selected`, `disabled`
- **Props:** `title`, `message`, `trailing`, `selected`, `disabled`, `onTap`

### Empty

- **Variants:** `default`
- **Props:** `title`, `message`, `actionText`, `onAction`

### Toast

- **Variants:** `info`, `success`, `warning`, `error`
- **Props:** `message`, `variant`, `duration`

### Modal

- **Variants:** `confirm`
- **Props:** `title`, `message`, `confirmText`, `cancelText`, `onConfirm`, `onCancel`

### RefreshLayout

- **Variants:** `refreshing`, `loadingMore`, `disabled`
- **Props:** `refreshing`, `loadingMore`, `onRefresh`, `onLoadMore`

### LoadingDialog

- **Variants:** `default`, `compact`
- **Props:** `visible`, `message`, `dismissible`

### DatePicker

- **Variants:** `default`, `disabled`
- **Props:** `value`, `onChange`, `min`, `max`, `placeholder`, `disabled`

### ChildSwitcher

- **Variants:** `chip`, `tabs`
- **Props:** `items`, `selectedId`, `onChange`, `disabled`

### ScoreRuleGrid

- **Variants:** `default`, `readOnly`
- **Props:** `rules`, `columns`, `onIncrement`, `disabled`

### RedeemCardGrid

- **Variants:** `default`, `frozen`
- **Props:** `items`, `availablePoints`, `frozen`, `onRedeem`, `disabled`

### CalendarHeatmap

- **Variants:** `month`
- **Props:** `yearMonth`, `cells`, `onSelectDay`, `showLegend`

### PrintSheet

- **Variants:** `pinyin`, `meaning`
- **Props:** `title`, `items`, `columns`, `footerFields`

### BalanceHero

- **Variants:** `default`, `compact`
- **Props:** `total`, `breakdown`, `suffix`

### CheckInStreakCard

- **Variants:** `default`
- **Props:** `streakDays`, `totalDays`, `weekProgress`, `onOpen`, `disabled`

### Checkbox

- **Variants:** `default`, `disabled`
- **Props:** `text`, `variant`, `checked`, `disabled`, `onChange`

### Collapse

- **Variants:** `default`, `disabled`
- **Props:** `title`, `message`, `expanded`, `disabled`, `onChange`

### Divider

- **Variants:** `default`, `disabled`
- **Props:** `variant`, `text`, `disabled`

### Radio

- **Variants:** `default`, `disabled`
- **Props:** `text`, `checked`, `disabled`, `onChange`

### SearchBar

- **Variants:** `default`, `disabled`
- **Props:** `value`, `placeholder`, `variant`, `disabled`, `onChange`

### SegmentedControl

- **Variants:** `default`, `disabled`
- **Props:** `options`, `selectedIndex`, `disabled`, `onSelect`

### StarRating

- **Variants:** `default`, `disabled`, `readonly`
- **Props:** `value`, `max`, `disabled`, `onChange`

### Avatar

- **Variants:** `default`, `primary`, `subtle`
- **Props:** `text`, `src`, `size`, `variant`

### Skeleton

- **Variants:** `default`, `pulse`
- **Props:** `rows`, `animated`, `avatar`

### Tooltip

- **Variants:** `default`
- **Props:** `text`, `placement`, `visible`, `children`

### Slider

- **Variants:** `default`, `disabled`
- **Props:** `value`, `min`, `max`, `step`, `disabled`, `onChange`

### TextArea

- **Variants:** `default`, `error`, `disabled`
- **Props:** `value`, `placeholder`, `rows`, `maxLength`, `disabled`, `onChange`

### Drawer

- **Variants:** `default`
- **Props:** `visible`, `title`, `placement`, `onClose`, `children`

### InputNumber

- **Variants:** `default`, `disabled`
- **Props:** `value`, `min`, `max`, `step`, `disabled`, `onChange`

### Swiper

- **Variants:** `default`
- **Props:** `items`, `index`, `autoplay`, `onChange`

### Tag

- **Variants:** `default`, `primary`, `success`, `warning`, `danger`
- **Props:** `text`, `closable`, `selected`, `disabled`, `variant`, `onClose`, `onTap`

### Fab

- **Variants:** `primary`, `default`
- **Props:** `icon`, `text`, `variant`, `disabled`, `onTap`

### TimePicker

- **Variants:** `default`, `disabled`
- **Props:** `value`, `placeholder`, `disabled`, `onChange`

### Upload

- **Variants:** `default`, `disabled`
- **Props:** `files`, `multiple`, `disabled`, `accept`, `onChange`, `onRemove`

### Table

- **Variants:** `default`, `striped`
- **Props:** `columns`, `rows`, `variant`, `emptyText`

### Tree

- **Variants:** `default`
- **Props:** `items`, `selectedId`, `expandedIds`, `onSelect`, `onExpand`

### Cascader

- **Variants:** `default`, `disabled`
- **Props:** `options`, `value`, `placeholder`, `disabled`, `onChange`

