const BREAKDOWN_LABELS = {
  balance: '余额',
  ruleScore: '规则分',
  streakBonus: '连续奖励',
  redeemTotal: '已兑换',
};

Component({
  properties: {
    total: { type: Number, value: 0 },
    breakdown: { type: Object, value: null },
    suffix: { type: String, value: '分' },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  data: {
    rows: [],
  },
  observers: {
    breakdown(breakdown) {
      if (!breakdown || typeof breakdown !== 'object') {
        this.setData({ rows: [] });
        return;
      }
      this.setData({
        rows: Object.keys(breakdown).map((key) => ({
          key,
          label: BREAKDOWN_LABELS[key] || key,
          value: String(breakdown[key]),
        })),
      });
    },
  },
});
