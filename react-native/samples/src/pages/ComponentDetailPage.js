import React, { useState } from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspAlert, TspAmount, TspAvatar, TspBadge, TspBalanceHero, TspBottomTab, TspButton, TspCalendarHeatmap, TspCard, TspCascader, TspCheckInStreakCard, TspCheckbox, TspChildSwitcher, TspChip, TspCollapse, TspDatePicker, TspDivider, TspDrawer, TspEmpty, TspFab, TspIconButton, TspInput, TspInputNumber, TspKeyValueLabel, TspListItem, TspLoadingDialog, TspModal, TspNotification, TspOptionSheet, TspPinInput, TspPrintSheet, TspProgress, TspRadio, TspRedeemCardGrid, TspRefreshLayout, TspScoreRuleGrid, TspSearchBar, TspSegmentedControl, TspSelect, TspSkeleton, TspSlider, TspStarRating, TspStepper, TspStickyFooter, TspSwitch, TspSwiper, TspTable, TspTabs, TspTag, TspTextArea, TspTextLink, TspTimePicker, TspToast, TspTooltip, TspTopBar, TspTree, TspUpload } from '@techskillplanet/planet-components-react-native';
import { categoryOf, componentDescriptions, descriptionKeyOf } from '../data/componentDocs';
import { PlanetIcon } from '../icons/PlanetIcon';

export function ComponentDetailPage({ name, theme, t }) {
  const translate = t || ((key, ...args) => componentDescriptions[name] || key);
  const [checked, setChecked] = useState(true);
  const [expanded, setExpanded] = useState(false);
  const [radioValue, setRadioValue] = useState('a');
  const [searchValue, setSearchValue] = useState('');
  const [segmentIndex, setSegmentIndex] = useState(0);
  const [starValue, setStarValue] = useState(3);
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
  const [dateValue, setDateValue] = useState('2026-08-26');
  const [childId, setChildId] = useState(1);
  const [showDrawer, setShowDrawer] = useState(false);
  const [sliderValue, setSliderValue] = useState(40);
  const [textAreaValue, setTextAreaValue] = useState('');
  const [inputNumberValue, setInputNumberValue] = useState(3);
  const [swiperIndex, setSwiperIndex] = useState(0);
  const [timeValue, setTimeValue] = useState('09:30');
  const [uploadFiles, setUploadFiles] = useState([{ id: '1', name: 'readme.md' }]);
  const [treeSelectedId, setTreeSelectedId] = useState('a1');
  const [treeExpandedIds, setTreeExpandedIds] = useState(['a']);
  const [cascaderValue, setCascaderValue] = useState([]);
  const state = {
    checked, setChecked, expanded, setExpanded, radioValue, setRadioValue,
    searchValue, setSearchValue, segmentIndex, setSegmentIndex, starValue, setStarValue,
    selectedOption, setSelectedOption, selectedTab, setSelectedTab, inputValue, setInputValue,
    showSheet, setShowSheet, showModal, setShowModal, showLoading, setShowLoading,
    refreshing, setRefreshing, loadingMore, setLoadingMore, refreshItems, setRefreshItems,
    dateValue, setDateValue, childId, setChildId,
    showDrawer, setShowDrawer, sliderValue, setSliderValue, textAreaValue, setTextAreaValue,
    inputNumberValue, setInputNumberValue, swiperIndex, setSwiperIndex,
    timeValue, setTimeValue, uploadFiles, setUploadFiles,
    treeSelectedId, setTreeSelectedId, treeExpandedIds, setTreeExpandedIds,
    cascaderValue, setCascaderValue,
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
    case 'Checkbox': return (
      <View style={{ gap: 10 }}>
        <TspCheckbox text="Agree" checked={s.checked} onChange={s.setChecked} theme={theme} />
        <TspCheckbox text="Disabled" checked disabled theme={theme} />
      </View>
    );
    case 'Radio': return (
      <View style={{ gap: 10 }}>
        <TspRadio text="Option A" checked={s.radioValue === 'a'} onChange={() => s.setRadioValue('a')} theme={theme} />
        <TspRadio text="Option B" checked={s.radioValue === 'b'} onChange={() => s.setRadioValue('b')} theme={theme} />
      </View>
    );
    case 'SearchBar': return <TspSearchBar value={s.searchValue} placeholder="Search…" onChange={s.setSearchValue} theme={theme} />;
    case 'SegmentedControl': return (
      <TspSegmentedControl
        options={['日', '周', '月']}
        selectedIndex={s.segmentIndex}
        onSelect={index => s.setSegmentIndex(index)}
        theme={theme}
      />
    );
    case 'StarRating': return (
      <View style={{ gap: 10 }}>
        <TspStarRating value={s.starValue} onChange={s.setStarValue} theme={theme} />
        <TspStarRating value={4} variant="readonly" theme={theme} />
      </View>
    );
    case 'Collapse': return (
      <TspCollapse
        title="Details"
        message="Expanded body uses theme tokens."
        expanded={s.expanded}
        onChange={s.setExpanded}
        theme={theme}
      />
    );
    case 'Divider': return (
      <View style={{ gap: 4 }}>
        <Text style={{ color: theme.textSecondary }}>Above</Text>
        <TspDivider text="Section" theme={theme} />
        <Text style={{ color: theme.textSecondary }}>Below</Text>
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
    case 'DatePicker': return <TspDatePicker value={s.dateValue} onChange={s.setDateValue} placeholder="选择日期" theme={theme} />;
    case 'ChildSwitcher': return (
      <View style={{ gap: 10 }}>
        <TspChildSwitcher items={[{ id: 1, label: '悦悦', emoji: '👧' }, { id: 2, label: '佑佑', emoji: '👦' }]} selectedId={s.childId} onChange={s.setChildId} theme={theme} />
        <TspChildSwitcher variant="tabs" items={[{ id: 1, label: '悦悦' }, { id: 2, label: '佑佑' }]} selectedId={s.childId} onChange={s.setChildId} theme={theme} />
      </View>
    );
    case 'ScoreRuleGrid': return (
      <TspScoreRuleGrid
        rules={[
          { id: 1, name: '按时认真完成作业', icon: '📝', value: 5, count: 1, dailyLimit: 1 },
          { id: 2, name: '作业潦草', icon: '✏️', value: -2, count: 0, dailyLimit: 2 },
        ]}
        columns="auto"
        onIncrement={() => s.showToast('+1')}
        theme={theme}
      />
    );
    case 'RedeemCardGrid': return (
      <TspRedeemCardGrid
        items={[{ id: 1, name: '零食', icon: '🍬', cost: 15 }, { id: 2, name: '游戏', icon: '🎮', cost: 30 }]}
        availablePoints={20}
        onRedeem={() => s.showToast('兑换')}
        theme={theme}
      />
    );
    case 'CalendarHeatmap': return (
      <TspCalendarHeatmap
        yearMonth="2026-08"
        cells={[{ date: '2026-08-01', level: 'full' }, { date: '2026-08-02', level: 'partial' }, { date: '2026-08-03', level: 'exempt' }]}
        theme={theme}
      />
    );
    case 'PrintSheet': return (
      <TspPrintSheet
        title="错字默写"
        variant="pinyin"
        items={[{ prompt: 'dǐng' }, { prompt: 'lù' }, { prompt: 'yàn' }, { prompt: 'xīn' }, { prompt: 'wǎn' }]}
        columns={5}
        theme={theme}
      />
    );
    case 'BalanceHero': return (
      <TspBalanceHero
        total={41}
        breakdown={{ balance: 40, ruleScore: 11, streakBonus: 5, redeemTotal: 15 }}
        theme={theme}
      />
    );
    case 'CheckInStreakCard': return (
      <TspCheckInStreakCard
        streakDays={7}
        totalDays={45}
        weekProgress={0.85}
        onOpen={() => s.showToast('打开打卡')}
        theme={theme}
      />
    );
    case 'Avatar': return (
      <View style={{ flexDirection: 'row', gap: 12, alignItems: 'center' }}>
        <TspAvatar text="技趣" theme={theme} />
        <TspAvatar text="SP" variant="primary" size="lg" theme={theme} />
        <TspAvatar text="A" variant="subtle" size="sm" theme={theme} />
      </View>
    );
    case 'Skeleton': return <TspSkeleton rows={3} animated avatar theme={theme} />;
    case 'Tooltip': return (
      <TspTooltip text="Sky Planet tip" placement="top" theme={theme}>
        <TspButton text="Press me" variant="default" theme={theme} />
      </TspTooltip>
    );
    case 'Slider': return (
      <TspSlider value={s.sliderValue} min={0} max={100} step={1} onChange={s.setSliderValue} theme={theme} />
    );
    case 'TextArea': return (
      <TspTextArea value={s.textAreaValue} placeholder="Write a note…" rows={3} onChange={s.setTextAreaValue} theme={theme} />
    );
    case 'Drawer': return (
      <View>
        <TspButton text="Open Drawer" variant="primary" onTap={() => s.setShowDrawer(true)} theme={theme} />
        <TspDrawer visible={s.showDrawer} title="Drawer" placement="bottom" onClose={() => s.setShowDrawer(false)} theme={theme}>
          <Text style={{ color: theme.textPrimary }}>Sky Planet drawer body.</Text>
        </TspDrawer>
      </View>
    );
    case 'InputNumber': return (
      <TspInputNumber value={s.inputNumberValue} min={0} max={10} step={1} onChange={s.setInputNumberValue} theme={theme} />
    );
    case 'Swiper': return (
      <TspSwiper items={['Slide A', 'Slide B', 'Slide C']} index={s.swiperIndex} onChange={s.setSwiperIndex} theme={theme} />
    );
    case 'Tag': return (
      <View style={{ flexDirection: 'row', flexWrap: 'wrap', gap: 8 }}>
        <TspTag text="default" theme={theme} />
        <TspTag text="primary" variant="primary" theme={theme} />
        <TspTag text="closable" closable onClose={() => s.showToast('closed')} theme={theme} />
      </View>
    );
    case 'Fab': return (
      <View style={{ flexDirection: 'row', gap: 12, alignItems: 'center' }}>
        <TspFab icon="+" theme={theme} />
        <TspFab icon="+" text="新建" theme={theme} />
        <TspFab icon="✎" text="默认" variant="default" theme={theme} />
      </View>
    );
    case 'TimePicker': return <TspTimePicker value={s.timeValue} placeholder="HH:mm" onChange={s.setTimeValue} theme={theme} />;
    case 'Upload': return <TspUpload files={s.uploadFiles} onChange={s.setUploadFiles} theme={theme} />;
    case 'Table': return (
      <TspTable
        columns={[{ key: 'name', title: '名称' }, { key: 'status', title: '状态' }]}
        rows={[{ name: 'Avatar', status: '就绪' }, { name: 'Tag', status: '新增' }]}
        variant="striped"
        theme={theme}
      />
    );
    case 'Tree': return (
      <TspTree
        items={[
          { id: 'a', label: '星球', children: [{ id: 'a1', label: '天空' }, { id: 'a2', label: '岛屿' }] },
          { id: 'b', label: '玩法', children: [{ id: 'b1', label: '闯关' }] },
        ]}
        selectedId={s.treeSelectedId}
        expandedIds={s.treeExpandedIds}
        onSelect={(id) => s.setTreeSelectedId(id)}
        onExpand={s.setTreeExpandedIds}
        theme={theme}
      />
    );
    case 'Cascader': return (
      <TspCascader
        options={[
          { value: 'asia', label: '亚洲', children: [{ value: 'cn', label: '中国' }, { value: 'jp', label: '日本' }] },
          { value: 'eu', label: '欧洲', children: [{ value: 'fr', label: '法国' }] },
        ]}
        value={s.cascaderValue}
        placeholder="请选择地区"
        onChange={s.setCascaderValue}
        theme={theme}
      />
    );
    default: return null;
  }
}
