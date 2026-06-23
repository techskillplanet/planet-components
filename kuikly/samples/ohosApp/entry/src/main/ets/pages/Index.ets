@Entry
@Component
struct Index {
  build() {
    Column() {
      Text('Kuikly page: BasicControlsSample')
        .fontSize(20)
        .fontWeight(FontWeight.Bold)
        .fontColor('#172B3A')
      Text('Wire this host to the Kuikly OHOS renderer after installing the OHOS Kotlin toolchain.')
        .fontSize(14)
        .fontColor('#345A66')
        .margin({ top: 12 })
    }
    .width('100%')
    .height('100%')
    .justifyContent(FlexAlign.Center)
    .padding(24)
    .backgroundColor('#DDF4FF')
  }
}
