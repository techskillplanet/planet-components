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
| Button | `primary`, `default`, `danger`, `text`, `link` | `text`, `variant`, `disabled`, `fullWidth`, `onTap` |
| Card | `default`, `subtle`, `selected`, `disabled` | `variant`, `selected`, `disabled`, `content` |
| Alert | `info`, `success`, `warning`, `error` | `title`, `message`, `variant` |
| Badge | `default`, `primary`, `success`, `warning`, `danger` | `text`, `variant`, `disabled` |
| Chip | `default`, `primary`, `success`, `warning`, `danger` | `text`, `variant`, `selected`, `disabled`, `onTap` |
| Input | `default`, `error`, `disabled` | `value`, `placeholder`, `variant`, `disabled`, `onChange` |
| Select | `default`, `disabled` | `options`, `selectedIndex`, `disabled`, `onSelect` |
| OptionSheet | `default`, `mobile` | `title`, `options`, `selectedIndex`, `visible`, `onSelect`, `onCancel` |
| Switch | `md`, `sm`, `loading`, `disabled` | `text`, `checked`, `checkedText`, `uncheckedText`, `loading`, `disabled`, `onChange` |
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

## Sample Coverage

Every component added to a library must appear in that stack's `samples` project with:

- default state
- disabled state where supported
- selected/checked/focused state where supported
- loading/error/success/warning state where supported
- a realistic usage scenario, not only an isolated rendering

