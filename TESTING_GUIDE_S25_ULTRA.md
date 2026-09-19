# 🧪 Testing Guide - The Clockwork Riddle on Samsung Galaxy S25 Ultra

## Device Setup

### Prerequisites
- Samsung Galaxy S25 Ultra with Android 16
- USB-C cable connected to development machine
- USB Debugging enabled on device
- ADB (Android Debug Bridge) installed and working
- ~100 MB free storage on device

### Enable USB Debugging
1. Go to **Settings → About Phone**
2. Tap **Build Number** 7 times to enable Developer Options
3. Go to **Settings → Developer Options**
4. Enable **USB Debugging**
5. When prompted on device: Accept the RSA key fingerprint

### Verify Connection
```bash
adb devices
# Should show: device-name    device (not offline)
```

---

## Installation

### Install Debug APK
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Expected Output:**
```
Success
```

### Verify Installation
```bash
adb shell pm list packages | grep clockworkriddle
# Should output: com.example.clockworkriddle
```

### Launch App
```bash
adb shell am start -n com.example.clockworkriddle/.MainActivity
```

---

## Testing Checklist

### ✅ Startup & Performance (5 min)

**Expected:** App launches smoothly in 2-3 seconds

- [ ] App launches without crash
- [ ] Menu displays correctly
- [ ] All UI elements visible and readable
- [ ] No lag or stuttering
- [ ] Navigation buttons responsive

**Check Logs:**
```bash
adb logcat | grep -i "clockworkriddle\|error"
# Should not show any errors or exceptions
```

---

### ✅ Main Menu (10 min)

**Expected:** Menu shows progress and all buttons work

#### Visual Elements
- [ ] Title "The Clockwork Riddle" displays clearly
- [ ] Menu background is dark (AMOLED optimized)
- [ ] Gold accent colors (#D4AF37) visible
- [ ] Progress counter shows "Progress 0/100"
- [ ] Cogs counter shows "Cogs 0/20"

#### Menu Buttons
- [ ] "New Game" button clickable
- [ ] "Continue" button clickable (if save exists)
- [ ] "Puzzle Book" button visible
- [ ] "Settings" button visible
- [ ] "Credits" button visible
- [ ] Each button responds to tap (no lag)

#### First Launch Actions
- [ ] Tap "New Game" → Clears progress
- [ ] Tap "Puzzle Book" → Shows 20 puzzle families
- [ ] Tap back → Returns to menu

---

### ✅ Puzzle Book (15 min)

**Expected:** All 20 puzzle families visible and selectable

#### Puzzle Family Display
- [ ] 20 puzzle families listed
- [ ] Each family has 5 levels (difficulty 1-5)
- [ ] Puzzle families named correctly:
  - Riddles, Number Squares, Sliding Blocks, Lights Out
  - Sliding Picture, Timing Gauges, Pattern Matching, Bulb Memory
  - Rotating Picture, Tower of Hanoi, Line Partition, Circuit Paths
  - Untangle, Solitaire, Liquid Jars, Domino Placement
  - Gear Train, Code Lock, One Stroke Path, Tangram

#### Level Progression
- [ ] Level 1 is gold/highlighted (current)
- [ ] Levels 2-5 are grayed out (locked)
- [ ] Tapping level 1 opens puzzle
- [ ] Tapping locked level shows "Not Yet Unlocked"

#### Navigation
- [ ] Scrolling works smoothly
- [ ] Back button returns to menu
- [ ] All UI transitions smooth

---

### ✅ Puzzle Gameplay (20 min)

**Test: Riddles Puzzle (Easiest)**

1. **Tap:** Riddles → Level 1

2. **Verify Screen Elements**
   - [ ] Puzzle title displays
   - [ ] Difficulty indicator (1/5)
   - [ ] Hint button visible
   - [ ] Reset button visible
   - [ ] Progress indication
   - [ ] Input field for answer

3. **Hint Button Test**
   - [ ] Tap "Hint" button
   - [ ] Hint text appears and is readable
   - [ ] Hint makes sense for the riddle
   - [ ] Hint button still functional

4. **Correct Answer Test**
   - [ ] Type correct answer in input field
   - [ ] Answer validation works
   - [ ] Success animation plays
   - [ ] Tap "Next" button
   - [ ] Progress updated (1/100 puzzles)
   - [ ] Return to Puzzle Book

5. **Reset Test**
   - [ ] Go back to Riddles Level 1
   - [ ] Tap "Reset"
   - [ ] Puzzle state cleared
   - [ ] Progress still shows 1/100 (not reset)

---

### ✅ Progression Milestones (30 min)

**Test: Story Unlock at 25 Puzzles**

1. **Complete Puzzles Rapidly**
   - [ ] Complete 5 Riddles (Level 1-5)
   - [ ] Complete 5 Number Squares (Level 1-5)
   - [ ] Complete 5 Sliding Blocks (Level 1-5)
   - [ ] Complete 5 Lights Out (Level 1-5)
   - [ ] Complete 5 Sliding Picture (Level 1-5)
   - Total: 25 puzzles

2. **Verify Progress Display**
   - [ ] Menu shows "Progress 25/100"
   - [ ] Menu shows "Cogs 5/20" (unlocked at 5, 10, 15, 20, 25)
   - [ ] No crashes during progression

3. **Story Scene Unlock**
   - [ ] After completing 25th puzzle, story scene should appear
   - [ ] Scene title displays ("Chapter 1: Awakening" or similar)
   - [ ] Scene text is readable
   - [ ] Animations are smooth
   - [ ] Continue button functions
   - [ ] Scene disappears after closing

---

### ✅ Cog Collection (10 min)

**Expected: Cogs unlock every 5 puzzles**

1. **Check Cog Collection Screen**
   - [ ] Menu shows "Collected Cogs (5)"
   - [ ] Tap to open Cog Collection screen
   - [ ] 20 cogs listed
   - [ ] First 5 are gold/collected
   - [ ] Remaining 15 are gray/locked
   - [ ] Cog names and descriptions visible

2. **Verify Cog Descriptions**
   - [ ] Cog 1: "Foundation Cog"
   - [ ] Cog 2: "Riddle Cog"
   - [ ] Cog 3: "Number Cog"
   - [ ] Cog 4: "Motion Cog"
   - [ ] Cog 5: "Light Cog"

3. **Progress Check**
   - [ ] Back to menu
   - [ ] Cog counter shows "5/20"

---

### ✅ Settings (10 min)

**Expected: Settings persist and function correctly**

1. **Access Settings**
   - [ ] Tap "Settings" from menu
   - [ ] Settings screen opens

2. **Test Music Toggle**
   - [ ] Toggle "Music" OFF
   - [ ] Toggle "Music" ON
   - [ ] Setting persists after closing app
   ```bash
   # Close app and reopen
   adb shell am force-stop com.example.clockworkriddle
   adb shell am start -n com.example.clockworkriddle/.MainActivity
   # Check if setting persisted
   ```

3. **Test Sound Toggle**
   - [ ] Toggle "Sound Effects" OFF
   - [ ] Toggle "Sound Effects" ON
   - [ ] Setting persists

4. **Test Haptics Toggle**
   - [ ] Toggle "Haptics" ON
   - [ ] Interact with buttons (should feel haptic feedback)
   - [ ] Toggle "Haptics" OFF
   - [ ] Buttons should have no haptic feedback
   - [ ] Setting persists

5. **Navigation**
   - [ ] Back button returns to menu

---

### ✅ Animations (10 min)

**Expected: Smooth 60 FPS animations**

1. **Visual Animations**
   - [ ] Opening menu animation smooth
   - [ ] Puzzle book list scrolls smoothly
   - [ ] Screen transitions are fluid
   - [ ] No visible stuttering or frame drops

2. **Specific Animation Tests**
   - [ ] Look for rotating gear elements
   - [ ] Look for pulse glow effects
   - [ ] Look for shimmer effects
   - [ ] Look for subtle floating elements

3. **Performance Monitoring**
   ```bash
   adb shell dumpsys gfxinfo com.example.clockworkriddle | tail -20
   # Look for "60.0 fps"
   ```

---

### ✅ Data Persistence (15 min)

**Expected: All progress saved**

1. **Complete 10 More Puzzles** (total: 35)
   - [ ] Progress displayed as "35/100"
   - [ ] Close app: `adb shell am force-stop com.example.clockworkriddle`
   - [ ] Reopen app
   - [ ] Progress still shows "35/100"
   - [ ] Same puzzles marked as completed

2. **Settings Persistence**
   - [ ] Change settings (music off, haptics on)
   - [ ] Close app
   - [ ] Reopen app
   - [ ] Settings match what you set

3. **Cog Collection Persistence**
   - [ ] Note current cogs (should be 7/20 at 35 puzzles)
   - [ ] Close and reopen app
   - [ ] Cog count remains same

---

### ✅ Final Challenge (Optional - Requires 95+ Puzzles)

**Expected: Available only at 95+ puzzles**

1. **Complete 95+ Puzzles** (if time permits)
   - Rapidly complete puzzles across families
   - Monitor progress toward 95

2. **Verify Challenge Unlock**
   - [ ] At 95+ puzzles, "The Final Challenge!" button appears on menu
   - [ ] Tap to open challenge screen
   - [ ] 5 challenge stages displayed
   - [ ] Each stage shows description and reward
   - [ ] Stages are navigable

---

### ✅ Memory & Performance (10 min)

**Memory Footprint Test**
```bash
adb shell dumpsys meminfo com.example.clockworkriddle
# Check "Total" line - should be 150-250 MB
```

**Expected:** Memory usage 150-200 MB during normal gameplay

**CPU Usage Test**
```bash
adb shell top -n 1 | grep clockworkriddle
# Check CPU usage - should be <5% when idle
```

---

### ✅ Crash & Stability (15 min)

**Stress Test: Rapid Interactions**

1. **Rapid Menu Navigation**
   - [ ] Tap menu buttons rapidly
   - [ ] No crashes
   - [ ] No stuck UI

2. **Rapid Puzzle Switching**
   - [ ] Open/close puzzles quickly
   - [ ] No crashes or memory leaks
   - [ ] UI remains responsive

3. **Orientation Changes**
   - [ ] Rotate device from portrait to landscape and back
   - [ ] App should adapt (or lock to portrait)
   - [ ] No crashes
   - [ ] UI elements remain visible

4. **Long Sessions**
   - [ ] Play for 10+ minutes
   - [ ] No lag or slowdown
   - [ ] No memory issues
   - [ ] Battery drain acceptable

---

### ✅ UI/UX Quality (15 min)

**Visual Polish**
- [ ] All text is readable
- [ ] Colors are vibrant and appropriate
- [ ] Gold (#D4AF37) accents visible throughout
- [ ] Dark theme is AMOLED-optimized (pure black #0a0a0a)
- [ ] No broken layouts
- [ ] Font sizes appropriate for 6.9" screen
- [ ] Touch targets are adequate size

**Navigation**
- [ ] Back button works on all screens
- [ ] No stuck navigation states
- [ ] Transitions between screens smooth
- [ ] Menu always accessible

**Input Handling**
- [ ] Text input responsive
- [ ] Button taps register immediately
- [ ] Scrolling smooth and predictable
- [ ] No input lag

---

## Issue Reporting

If you find any issues, collect this information:

### Crash Report
```bash
# Get crash logs
adb logcat | grep -A20 "FATAL\|Exception"
```

### Bug Report Template
- **Issue:** What went wrong?
- **Steps to Reproduce:** How to make it happen again?
- **Expected:** What should happen?
- **Actual:** What actually happens?
- **Logs:** Include logcat output
- **Device:** S25 Ultra, Android 16
- **Reproducible:** Always/Sometimes/Once?

---

## Test Results Summary

After completing all tests, fill out:

```
TESTING REPORT - The Clockwork Riddle
Device: Samsung Galaxy S25 Ultra (Android 16)
Date: ___________

STARTUP & PERFORMANCE:     ✅ / ❌ / ⚠️
MAIN MENU:                 ✅ / ❌ / ⚠️
PUZZLE BOOK:               ✅ / ❌ / ⚠️
PUZZLE GAMEPLAY:           ✅ / ❌ / ⚠️
PROGRESSION MILESTONES:    ✅ / ❌ / ⚠️
COG COLLECTION:            ✅ / ❌ / ⚠️
SETTINGS:                  ✅ / ❌ / ⚠️
ANIMATIONS:                ✅ / ❌ / ⚠️
DATA PERSISTENCE:          ✅ / ❌ / ⚠️
FINAL CHALLENGE:           ✅ / ❌ / ⚠️ / N/A
MEMORY & PERFORMANCE:      ✅ / ❌ / ⚠️
CRASH & STABILITY:         ✅ / ❌ / ⚠️
UI/UX QUALITY:             ✅ / ❌ / ⚠️

Overall Status: ✅ PASS / ⚠️ PASS WITH MINOR ISSUES / ❌ FAIL

Issues Found: _____ (describe below)
```

---

## Quick Commands Reference

```bash
# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.example.clockworkriddle/.MainActivity

# View logs (realtime)
adb logcat | grep clockworkriddle

# Check memory
adb shell dumpsys meminfo com.example.clockworkriddle

# Check performance
adb shell dumpsys gfxinfo com.example.clockworkriddle

# Uninstall app
adb uninstall com.example.clockworkriddle

# Force stop app
adb shell am force-stop com.example.clockworkriddle

# Clear app data
adb shell pm clear com.example.clockworkriddle
```

---

## Success Criteria

✅ **Testing is successful when:**
- App launches without crashes
- All 20 puzzle families are accessible
- Puzzles can be solved
- Progress persists after app restart
- Settings work and persist
- No memory leaks
- Animations are smooth
- No UI glitches or crashes
- Device battery drain is acceptable

---

**Expected Test Duration:** 2-3 hours for comprehensive testing

**Minimum Test Duration:** 30 minutes (core functionality only)

Good luck testing! 🎮✅
