/**
 * Component documentation registry for TechSkillPlanet React Web controls.
 * Used by the sample app (BasicControlsSample) to render an interactive catalog.
 */

/** Component category groups in display order. */
export const componentCategories = [
  'Actions',
  'Surfaces',
  'Feedback',
  'Inputs',
  'Navigation',
  'Data'
];

/** Full component documentation entries. */
export const componentDocs = [
  // Actions
  { name: 'Button', component: 'TspButton', category: 'Actions', description: 'Primary action button with primary, default, danger, text and link variants.', props: ['text', 'variant', 'disabled', 'fullWidth', 'theme', 'onTap'], variants: ['primary', 'default', 'danger', 'text'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Chip', component: 'TspChip', category: 'Actions', description: 'Selectable tag for filtering and lightweight actions.', props: ['text', 'variant', 'selected', 'disabled', 'theme', 'onTap'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'IconButton', component: 'TspIconButton', category: 'Actions', description: 'Icon-based button for toolbars and quick actions.', props: ['icon', 'selected', 'disabled', 'variant', 'theme', 'onTap'], variants: ['default', 'primary'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'TextLink', component: 'TspTextLink', category: 'Actions', description: 'Text link button for secondary navigation.', props: ['text', 'inverse', 'theme', 'onTap'], variants: ['default', 'inverse'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },

  // Surfaces
  { name: 'Card', component: 'TspCard', category: 'Surfaces', description: 'Content container for grouped content with optional selection.', props: ['variant', 'selected', 'disabled', 'theme', 'children'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'ListItem', component: 'TspListItem', category: 'Surfaces', description: 'List row with title, message, trailing element and states.', props: ['title', 'message', 'trailing', 'selected', 'disabled', 'theme', 'onTap'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Empty', component: 'TspEmpty', category: 'Surfaces', description: 'Empty state placeholder with illustration, title and action.', props: ['title', 'message', 'actionText', 'theme', 'onAction'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },

  // Feedback
  { name: 'Alert', component: 'TspAlert', category: 'Feedback', description: 'Inline alert for success, warning, error and info messages.', props: ['title', 'message', 'variant', 'theme'], variants: ['info', 'success', 'warning', 'error'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Badge', component: 'TspBadge', category: 'Feedback', description: 'Short text status badge for counts and labels.', props: ['text', 'variant', 'disabled', 'theme'], variants: ['default', 'primary', 'success', 'warning', 'danger'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Progress', component: 'TspProgress', category: 'Feedback', description: 'Progress bar with semantic color variants.', props: ['progress', 'variant', 'theme'], variants: ['primary', 'success', 'warning', 'danger'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Notification', component: 'TspNotification', category: 'Feedback', description: 'Notification card for task reminders and status alerts.', props: ['title', 'message', 'variant', 'theme'], variants: ['info', 'alert'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Toast', component: 'TspToast', category: 'Feedback', description: 'Temporary toast notification for brief feedback.', props: ['message', 'variant', 'duration', 'theme'], variants: ['info', 'success', 'warning', 'error'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Modal', component: 'TspModal', category: 'Feedback', description: 'Confirmation dialog with confirm and cancel actions.', props: ['title', 'message', 'confirmText', 'cancelText', 'theme', 'onConfirm', 'onCancel'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },

  // Inputs
  { name: 'Input', component: 'TspInput', category: 'Inputs', description: 'Single-line text input with error and disabled states.', props: ['value', 'placeholder', 'variant', 'disabled', 'theme', 'onChange'], variants: ['default', 'error'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Select', component: 'TspSelect', category: 'Inputs', description: 'Select trigger that opens a bottom OptionSheet.', props: ['options', 'selectedIndex', 'disabled', 'theme', 'onSelect'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'OptionSheet', component: 'TspOptionSheet', category: 'Inputs', description: 'Bottom sheet option picker for mobile-style selection.', props: ['title', 'options', 'selectedIndex', 'visible', 'theme', 'onSelect', 'onCancel'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Switch', component: 'TspSwitch', category: 'Inputs', description: 'Toggle switch with loading and disabled states.', props: ['text', 'checked', 'checkedText', 'uncheckedText', 'loading', 'disabled', 'theme', 'onChange'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'PinInput', component: 'TspPinInput', category: 'Inputs', description: 'PIN/verification code input with secure mode.', props: ['value', 'cellCount', 'secure', 'theme', 'onChange', 'onComplete'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },

  // Navigation
  { name: 'TopBar', component: 'TspTopBar', category: 'Navigation', description: 'Top navigation bar with title and optional back button.', props: ['title', 'showBack', 'backgroundColor', 'theme', 'onBack'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'BottomTab', component: 'TspBottomTab', category: 'Navigation', description: 'Primary page bottom tab bar for 3-5 entries.', props: ['tabs', 'selectedKey', 'theme', 'onSelect'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Tabs', component: 'TspTabs', category: 'Navigation', description: 'In-page segmented tabs for content filtering.', props: ['tabs', 'selectedIndex', 'theme', 'onSelect'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'StickyFooter', component: 'TspStickyFooter', category: 'Navigation', description: 'Fixed bottom action area for primary page actions.', props: ['content', 'theme', 'children'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },

  // Data
  { name: 'Amount', component: 'TspAmount', category: 'Data', description: 'Monetary/numeric display with currency symbol and cycle.', props: ['symbol', 'value', 'cycle', 'symbolAfter', 'strikeThrough', 'theme'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'KeyValueLabel', component: 'TspKeyValueLabel', category: 'Data', description: 'Key-value pair display for summary information.', props: ['label', 'value', 'theme'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] },
  { name: 'Stepper', component: 'TspStepper', category: 'Data', description: 'Step progress indicator for 3-5 steps.', props: ['stepCount', 'currentStep', 'theme'], variants: ['default'], platforms: ['React Web', 'React Native', 'Flutter', 'iOS SwiftUI', 'Android View'] }
];
