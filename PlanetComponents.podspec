# CocoaPods publish entry. Source of truth remains ios-swiftui/library.
# Paths are relative to the git repository root (required after trunk push).
Pod::Spec.new do |s|
  s.name             = 'PlanetComponents'
  s.version          = '0.2.0'
  s.summary          = 'TechSkillPlanet Planet Components for SwiftUI.'
  s.description      = <<-DESC
    Sky Planet SwiftUI component library for TechSkillPlanet.
    Public types use the Tsp* prefix and match the shared component contract.
                       DESC
  s.homepage         = 'https://github.com/techskillplanet/planet-components'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'TechSkillPlanet' => 'https://github.com/techskillplanet' }
  s.source           = {
    :git => 'https://github.com/techskillplanet/planet-components.git',
    :tag => s.version.to_s
  }
  s.ios.deployment_target = '15.0'
  s.osx.deployment_target = '13.0'
  s.swift_version    = '5.9'
  s.module_name      = 'PlanetComponents'
  s.source_files     = 'ios-swiftui/library/Sources/PlanetComponents/**/*.swift'
  s.frameworks       = 'SwiftUI', 'Foundation'
end
