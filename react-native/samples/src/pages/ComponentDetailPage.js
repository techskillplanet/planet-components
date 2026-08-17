import React, { useState } from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspAlert, TspAmount, TspBadge, TspBottomTab, TspButton, TspCard, TspChip, TspEmpty, TspIconButton, TspInput, TspKeyValueLabel, TspListItem, TspLoadingDialog, TspModal, TspNotification, TspOptionSheet, TspPinInput, TspProgress, TspRefreshLayout, TspSelect, TspStepper, TspStickyFooter, TspSwitch, TspTabs, TspTextLink, TspToast, TspTopBar } from '@techskillplanet/planet-components-react-native';
import { categoryOf, componentDescriptions, descriptionKeyOf } from '../data/componentDocs';
import { PlanetIcon } from '../icons/PlanetIcon';

export function ComponentDetailPage({ name, theme, t }) {
  const translate = t || ((key, ...args) => componentDescriptions[name] || key);
  const [checked, setChecked] = useState(true);
  const [selectedOption, setSelectedOption] = useState(1);
  const [selectedTab, setSelectedTab] = useState(0);
  const [inputValue, setInputValue] = useState('');
  const [showSheet, setShowSheet] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [showLoading, setShowLoading] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [loadingMore, setLoadingMore] = useState(false);
  const [refreshItems, setRefreshItems] = useState([1, 2, 3, 4, 5, 6]);
  const [toast, setToast] = useState('');
  const state = {
    checked, setChecked, selectedOption, setSelectedOption, selectedTab, setSelectedTab, inputValue, setInputValue,
    showSheet, setShowSheet, showModal, setShowModal, showLoading, setShowLoading,
    refreshing, setRefreshing, loadingMore, setLoadingMore, refreshItems, setRefreshItems,
    showToast: message => { setToast(message); setTimeout(() => setToast(''), 1600); },
  };
  const category = categoryOf(name);
  return (
    <View style={{ flex: 1 }}>
      <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
        <TspCard theme={theme}>
          <Text style={{ color: theme.brandPrimary, fontWeight: '900' }}>{translate(`sample/group/${category.toLowerCase()}`)}</Text>
          <Text style={{ marginTop: 8, color: theme.textPrimary, fontSize: 26, fontWeight: '900' }}>Tsp{name}</Text>
          <Text style={{ marginTop: 8, color: theme.textSecondary }}>{translate(descriptionKeyOf(name))}</Text>
        </TspCard>
        <TspCard theme={theme}>
          <Text style={{ color: theme.textPrimary, fontWeight: '900', marginBottom: 12 }}>{translate('sample/section/usage')}</Text>
          {preview(name, theme, state, translate)}
        </TspCard>
        <TspCard theme={theme}>
          <Text style={{ color: theme.textPrimary, fontWeight: '900' }}>{translate('sample/section/stack_sync')}</Text>
          <View style={{ flexDirection: 'row', flexWrap: 'wrap', gap: 8, marginTop: 12 }}>
            {['React', 'Vue', 'Android', 'iOS', 'Kuikly', 'RN'].map(item => <TspBadge key={item} text={item} theme={theme} />)}
          </View>
        </TspCard>
      </ScrollView>
      {!!toast && <View style={{ position: 'absolute', left: 18, right: 18, bottom: 24 }}><TspToast message={toast} variant="success" theme={theme} /></View>}
    </View>
  );
}

function preview(name, theme, s, translate) {
  switch (name) {
    case 'Button': return <View style={{ gap: 10 }}><TspButton text="primary" variant="primary" theme={theme} /><TspButton text="default" theme={theme} /><TspButton text="danger" variant="danger" theme={theme} /><TspButton text="text" variant="text" theme={theme} /></View>;
    case 'Card': return <TspCard selected theme={theme}><Text style={{ color: theme.textPrimary }}>Selected card</Text></TspCard>;
    case 'Alert': return <View style={{ gap: 10 }}><TspAlert title="success" message="Theme is applied." variant="success" theme={theme} /><TspAlert title="warning" message="Check required fields." variant="warning" theme={theme} /></View>;
    case 'Badge': return <View style={{ flexDirection: 'row', flexWrap: 'wrap', gap: 8 }}>{['default', 'primary', 'success', 'warning', 'danger'].map(v => <TspBadge key={v} text={v} variant={v} theme={theme} />)}</View>;
    case 'Chip': return <View style={{ flexDirection: 'row', gap: 8 }}><TspChip text="Default" theme={theme} /><TspChip text="Selected" selected theme={theme} /><TspChip text="Disabled" disabled theme={theme} /></View>;
    case 'Input': return <View style={{ gap: 10 }}><TspInput value={s.inputValue} placeholder="Input" onChange={s.setInputValue} theme={theme} /><TspInput placeholder="Required" variant="error" theme={theme} /></View>;
    case 'Select': return <TspSelect options={['A', 'B', 'C']} selectedIndex={s.selectedOption} onSelect={s.setSelectedOption} theme={theme} />;
    case 'OptionSheet': return <><TspButton text="Open OptionSheet" variant="primary" onPress={() => s.setShowSheet(true)} theme={theme} /><TspOptionSheet visible={s.showSheet} options={['A', 'B', 'C']} selectedIndex={s.selectedOption} theme={theme} onCancel={() => s.setShowSheet(false)} onSelect={index => { s.setSelectedOption(index); s.setShowSheet(false); }} /></>;
    case 'Switch': return (
      <View style={{ gap: 10 }}>
        <TspSwitch text="Switch" checked={s.checked} onChange={s.setChecked} theme={theme} />
        <TspSwitch text="Small" checked={s.checked} onChange={s.setChecked} variant="sm" theme={theme} />
        <TspSwitch text="Loading" checked loading theme={theme} />
        <TspSwitch text="Disabled" checked={false} disabled theme={theme} />
      </View>
    );
    case 'Progress': return <View style={{ gap: 10 }}><TspProgress progress={38} theme={theme} /><TspProgress progress={68} variant="success" theme={theme} /><TspProgress progress={82} variant="danger" theme={theme} /></View>;
    case 'TopBar': return <TspTopBar title={translate('sample/app/title')} showBack backIcon={<PlanetIcon name="back" />} theme={theme} immersive={false} />;
    case 'BottomTab': return <TspBottomTab tabs={[{ key: 'learn', title: translate('sample/tab/home'), icon: <PlanetIcon name="home" /> }, { key: 'settings', title: translate('sample/tab/settings'), icon: <PlanetIcon name="settings" /> }]} selectedKey="learn" theme={theme} />;
    case 'Tabs': return <TspTabs tabs={['全部', '已学', '未学']} selectedIndex={s.selectedTab} onSelect={s.setSelectedTab} theme={theme} />;
    case 'Amount': return <View style={{ gap: 10 }}><TspAmount symbol="$" value="128.80" cycle="month" theme={theme} /><TspAmount symbol="$" value="199.00" strikeThrough theme={theme} /></View>;
    case 'IconButton': return (
      <View style={{ flexDirection: 'row', gap: 8 }}>
        <TspIconButton icon={<PlanetIcon name="music" />} selected theme={theme} />
        <TspIconButton icon={<PlanetIcon name="check" />} theme={theme} />
        <TspIconButton icon={<PlanetIcon name="close" />} disabled theme={theme} />
      </View>
    );
    case 'KeyValueLabel': return <TspKeyValueLabel label="Progress" value="12/48" theme={theme} />;
    case 'Notification': return <TspNotification title="通知" message="继续学习。" variant="alert" theme={theme} />;
    case 'TextLink': return <TspTextLink text="Text Link" theme={theme} />;
    case 'Stepper': return <View style={{ gap: 10 }}><TspStepper stepCount={3} currentStep={2} theme={theme} /><TspStepper stepCount={5} currentStep={3} theme={theme} /></View>;
    case 'StickyFooter': return <TspStickyFooter theme={theme}><TspButton text="Sticky Footer" variant="primary" theme={theme} /></TspStickyFooter>;
    case 'PinInput': return <View style={{ gap: 10 }}><TspPinInput value="2468" secure theme={theme} /><TspPinInput value="123" cellCount={6} theme={theme} /></View>;
    case 'ListItem': return <View style={{ gap: 10 }}><TspListItem title="列表项" message="选中状态" trailing="›" selected theme={theme} /><TspListItem title="不可点击" message="禁用状态" disabled theme={theme} /></View>;
    case 'Empty': return <TspEmpty title="空状态" message="暂无记录。" actionText="操作" theme={theme} />;
    case 'Toast': return <TspButton text="Show Toast" variant="primary" theme={theme} onPress={() => s.showToast('已保存')} />;
    case 'Modal': return <><TspButton text="Open Modal" theme={theme} onPress={() => s.setShowModal(true)} /><TspModal visible={s.showModal} title="确认" message="组件弹窗完整显示。" confirmText="确定" cancelText="取消" theme={theme} onCancel={() => s.setShowModal(false)} onConfirm={() => s.setShowModal(false)} /></>;
    case 'RefreshLayout': return <View style={{ height: 280 }}><TspRefreshLayout style={{ flex: 1 }} theme={theme} refreshing={s.refreshing} loadingMore={s.loadingMore} onRefresh={() => { s.setRefreshing(true); setTimeout(() => { s.setRefreshItems([1, 2, 3, 4, 5, 6]); s.setRefreshing(false); s.showToast(translate('sample/toast/refreshed')); }, 900); }} onLoadMore={() => { if (s.loadingMore || s.refreshing) return; s.setLoadingMore(true); setTimeout(() => { s.setRefreshItems(items => [...items, items.length + 1, items.length + 2]); s.setLoadingMore(false); }, 900); }}>{s.refreshItems.map(item => <TspListItem key={item} title={`列表项 ${item}`} message="下拉刷新，上拉加载更多" trailing="›" theme={theme} />)}</TspRefreshLayout></View>;
    case 'LoadingDialog': return <><TspButton text="Show Loading" variant="primary" theme={theme} onPress={() => { s.setShowLoading(true); setTimeout(() => s.setShowLoading(false), 1400); }} /><TspButton text="Compact Loading" theme={theme} onPress={() => { s.setShowLoading('compact'); setTimeout(() => s.setShowLoading(false), 1400); }} /><TspLoadingDialog visible={!!s.showLoading} variant={s.showLoading === 'compact' ? 'compact' : 'default'} message="同步主题中..." theme={theme} /></>;
    default: return null;
  }
}
