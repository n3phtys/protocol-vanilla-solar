package nephtys.loom.protocol.shared

/**
  * Created by Christopher on 05.01.2017.
  */
object Loaded {



  /*This charm and spell data is copied from https://docs.google.com/spreadsheets/d/18FYdnXLYj0JnBNxNSGIZyi_FZcg085qCyUYoCEtac_8/edit#gid=987381560
  *
  * it's improved by explicit dependencies
  * */
  private final val solarCharmsCSV : Seq[String] = Seq (
      """Wise Arrow,1m,Supplemental,Uniform,Instant,"Reduce cover by -1, including no cover. After aiming, can hit around full cover, at only +3 defense.",255,Archery,2,1
        |Sight Without Eyes,1m,Reflexive,None,One Tick,Can fire without penalty due to visual conditions. at 3ess can see through cover.,255,Archery,3,1
        |Blood Without Balance,3m,Reflexive,Decisive-Only,Instant,"After gaining the benefit of a distract gambit and if your new initiative allows you to act immediately, you may fire up to long range without aiming. If <7 initiative, +ess dice base damage.",256,Archery,4,1
        |Force Without Fire,3m,Supplemental,Withering-Only,Instant,"If damge is at least as much as the target's stamina, don't gain initiative, but target is knocked down and back 1 range band. Can end a rush.",256,Archery,4,1
        |Trance of Unhesitating Speed,"4m, 1wp",Simple,Decisive-Only,Instant,"Spread init over many attacks, on single or multiple targets. Each 10 on attack gives +1 base damage. Doesn't need to aim.",256,Archery,3,1
        |Phantom Arrow Technique,1m,Reflexive,None,Instant,"Fire without ammo. Imbue ammo with an intimacy. At 3ess, arrows are stuck.",257,Archery,3,1
        |Fiery Arrow Technique,2m,Supplemental,Decisive-Only,Instant,"Set ammo on fire, gives +1 succ to damage. If it does at least 3HL, target is on fire.",257,Archery,4,1
        |There Is No Wind,3m,Reflexive,Dual,Instant,"Ignore non-visual penalties. Wither accuracy is set to short range. With Awareness, can attack from extreme long range.",257,Archery,5,2
        |Accuracy Without Distance,"1m, 1wp",Reflexive,Decisive-Only,Instant,"Aim instantly, the +3 bonus is converted into non-charm succ.",257,Archery,5,2
        |Accuracy Without Distance x2,"1m, 1wp",Reflexive,Dual,Instant,Can use with withering on a crashed target. Roll damage even if no succ.,257,Archery,5,5
        |Arrow Storm Technique,"5m, 1wp",Simple,Decisive-Only,Instant,Hit 3x ess targets within medium range of target.,257,Archery,5,2
        |Flashing Vengeance Draw,3m,Supplemental,None,Instant,"Add ess succ to Join Battle, if attacking before target, it's unblockable.",258,Archery,5,2
        |Hunter's Swift Answer,"5m, 1wp",Reflexive,Uniform,Instant,Extra short or close ranged attack if win a disengage.,258,Archery,5,2
        |Immaculate Golden Bow,"5m, 1wp",Simple,None,Scene,Create a glowing powerbow from essence. 4m to make heavy cover. Rebuy for evocations.,258,Archery,4,2
        |Dazzling Flare Attack,3m,Reflexive,Decisive-Only,Instant,"Strengthens ammo from FAA. Creates a bright flare, disrupting shadow cover.",258,Archery,5,2
        |Seven Omens Shot,"3m, 1wp",Simple,Decisive-Only,Instant,Aim for 3 rounds to convert the +3 into non-charm succ and extra succ add to raw damage. AWD shortens it by 1 round. Killing gives 1 WP.,258,Archery,5,3
        |Revolving Bow Discipline,"6m, 1wp",Simple,"Perilous, Withering-Only",Instant,"Keeping hitting the same target within short range, until you miss or they crash.",259,Archery,5,3
        |Finishing Snipe,7m,Reflexive,Decisive-Only,Instant,Free attack on someone within range if they crash.,259,Archery,5,3
        |Rain of Feathered Death,"3m/copy, 1wp",Simple,Decisive-Only,Instant,Make (Dex) hits against a target.,259,Archery,5,3
        |Shadow-Seeking Arrow,"3m, 2i",Reflexive,Uniform,Instant,"Free attack when spotting someone, without needing to aim.",259,Archery,5,3
        |Searing Sunfire Interdiction,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,Diff 3 gambit without needing to aim. Forces target to delay their turn. Rebuy for lots of extra stuff.,259,Archery,5,3
        |Searing Sunfire Interdiction x2,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,"Lowers diff of gambit to 2, don't lose Init on succ",259,Archery,5,3
        |Searing Sunfire Interdiction x3,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,Resets attack if you drop target from high Init to lower,259,Archery,5,4
        |Searing Sunfire Interdiction x4,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,"Can use twice consecutively on the same target, no wp cost on 2nd shot.",259,Archery,5,5
        |Searing Sunfire Interdiction x5,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,Can use repeatedly on same target if you keep hitting. Ends with miss or when target loses a turn. Gain 1wp if target loses turn.,259,Archery,5,6
        |Searing Sunfire Interdiction x6,"4m, 1i, 1wp",Simple,Decisive-Only,Instant,Can target new opponent if you drop someone to tick 0 or lower.,259,Archery,5,6
        |Solar Spike,"5m, 1wp",Simple,Decisive-Only,Instant,"If higher initiative than target, can hit from medium or long without aiming, and can hit from extreme with an aim. Raw damage is WP or Intimacy.",260,Archery,5,3
        |Heart-Eating Incineration,"3m, 3a",Reflexive,Decisive-Only,Instant,"When using SS and anima is at bonfire, fires anima to add initiative to SS's raw damage.",260,Archery,5,3
        |Dust and Ash Sleight,3m,Reflexive,Decisive-Only,Instant,"Shortens the aim action of SOS by 1 round, giving up the non charm successes. Rebuy to gain the succ again.",260,Archery,5,4
        |Dust and Ash Sleight x2,3m,Reflexive,Decisive-Only,Instant,Adds back loss succ to the attack.,260,Archery,5,5
        |Heavens Crash Down,"6m, 2i, 1wp",Reflexive,"Clash, Perilous, Withering-Only",Instant,"If in -4 HLs, may clash within short range. Gains ess succ and damage roll uses double 9s. Rebuy to use while crashed.",260,Archery,5,4
        |Heavens Crash Down x2,"6m, 2i, 1wp",Reflexive,"Clash, Withering-Only",Instant,Can spend 2i cost even when crashed going into further negatives.,260,Archery,5,5
        |Streaming Arrow Stance,6m,Simple,None,Scene,Can shoot a crashed target at medium or long range without aiming.,260,Archery,5,4
        |Whispered Prayer of Judgement,1m,Supplemental,Uniform,Instant,"After aiming, add ess damage to the attack.",261,Archery,5,5
        |Graceful Crane Stance,3m,Reflexive,None,Scene,Stand with great balance on unstable or narrow surfaces.,261,Athletics,1,1
        |Monkey Leap Technique,2m,Supplemental,None,Instant,Leap up or forward 1 range band. Cheaper if used again.,261,Athletics,2,1
        |Soaring Crane Leap,3m,Reflexive,None,Instant,Glide down 2 range bands without taking damage. Use movement to glide forward 1 range band while falling. Thrust helps.,261,Athletics,3,1
        |Foe-Vaulting Method,3i,Reflexive,None,Instant,Jump over target to give a surprise attack.,261,Athletics,2,1
        |Lightning Speed,3m,Supplemental,None,Instant,Add 1 suc and reroll recurring 5s and 6s for a rush or test of speed.,262,Athletics,3,1
        |Winning Stride Discipline,-,Permanent,None,Permanent,"Each interval where you're the fastest, gain 2m for athletics charms or 2i.",262,Athletics,4,1
        |Increasing Strength Exercise,3m or 3i/dot,Simple,None,Scene,"Spend 3m or 3i per 1 extra dot of strength, and +1 base decisive damage. Up by ess or to double strength.",262,Athletics,3,1
        |Ten Ox Meditation,"2m, 3 sux/dot",Supplemental,None,One Feat,"Roll strength+athletics to add 1 dot of strength per 3succ, for a feat of strength's requirements.",262,Athletics,5,1
        |Thunderbolt Attack Prana,"4m, 1wp",Supplemental,Decisive-Only,Instant,"Leap at a target within short range, doubling damage after roll. If target was aerial, may fall 1 range without damage.",262,Athletics,3,1
        |Feather Foot Style,3m,Reflexive,Mute,One Run,Can run on water or other weak surfaces. At full speed won't be harmed by lava or acid. At 2ess can pause and go slower for the length of 1 stunt. Still need to run to not be hurt by lava or acid.,263,Athletics,3,1
        |Spider Foot Style,3m,Reflexive,Mute,Ess+1 Turns,"Walk on walls, ceilings and other sufraces defying gravity.",263,Athletics,4,1
        |Unbound Eagle Approach,- (2m),Permanent,None,Permanent,Add 2m to SCL to glide without thrust.,264,Athletics,4,2
        |Leaping Tiger Attack,"4m, 1wp",Supplemental,Dual,Instant,Leap toward a target up to medium range and attack. Doubling post-soak damage dice and adding ess to base decisive damage.,264,Athletics,5,2
        |Racing Hare Method,"5m, 1wp",Reflexive,None,One Hour,"Move 3 range bands per turn. In battle only 1 per turn, but double 9s on rush.",264,Athletics,4,2
        |Onrush Burst Method,-,Permanent,None,Permanent,"After rushing, gain 3 motes for athletics charms this turn.",264,Athletics,3,2
        |Arete-Driven Marathon Stride,-,Permanent,None,Permanent,"If behind in a test of speed or a rush, enemy 10s give you 1 succ.",264,Athletics,5,2
        |Armor-Eating Strike,1m,Supplemental,Decisive-Only,Instant,Close range decisive ignores (Strength) hardness.,264,Athletics,3,2
        |Thunder's Might,5m,Reflexive,None,Instant,Reroll all non-successes for a feat of strength.,265,Athletics,5,2
        |Mountain-Crossing Leap Technique,"7m, 1wp",Simple,None,Leaping,"Leap up to 4 range bands forward or 3 upward, but no less than 3.",265,Athletics,5,3
        |Eagle-Wing Style,"5m, 1wp",Reflexive,None,Indefinite,Leap uo 2 range bands and fly 1 range band per turn by paying 2m or 2i. Attacking keeps you airborne.,265,Athletics,5,3
        |Demon-Wasting Rush,"5m, 1wp",Supplemental,None,Instant,"Can rush from medium range, if succesful, get extra moves toward target.",265,Athletics,5,3
        |Hurricane Spirit Speed,1i/sux,Supplemental,None,Instant,Spend 1i per succ for a rush or a test of speed.,265,Athletics,5,3
        |Godspeed Steps,"4m, 1wp",Reflexive,None,Instant,Can rush up to 3 range bands away. Removes flurry penalty for attacking after rush.,265,Athletics,5,3
        |Power Suffusing Form Technique,4m,Supplemental,None,Instant,Add (strength) non-charm dice to a feat of strength.,266,Athletics,5,3
        |Legion Aurochs Method,-,Permanent,None,Permanent,"For feats of strength, athletics excellency is half price.",266,Athletics,5,3
        |Triumph-Forged God-Body,-,Permanent,None,Permanent,Every athletics roll gains double 9s.,266,Athletics,5,3
        |One Extra Step,-,Permanent,None,Permanent,"Once per scene, take a 2nd movement action on your turn.",266,Athletics,5,4
        |Bonfire Anima Wings,-,Permanent,None,Permanent,"When using EWS and anima is at bonfire, your attacks benefit from OBM.",266,Athletics,5,4
        |Aegis of Unstoppable Force,-,Permanent,None,Permanent,Feats of strength lower difficulty by 2.,266,Athletics,5,4
        |Living Wind Approach,-,Permanent,None,Permanent,"Once per scene, autowin at a rush, or gain 1 more success than enemy in a test of speed interval.",266,Athletics,5,5
        |Nine Aeons Thew,"1m, 1wp",Supplemental,None,Instant,"Gain double 7s for feats of strength, and meet the requirements for all feats of strength.",267,Athletics,5,5
        |Sensory Acuity Prana,5m,Reflexive,None,Scene,"Double 9s, if using Unsurpassed (Sense) Discipline, reroll recurring 6s.",267,Awareness,2,1
        |Surprise Anticipation Method,-,Reflexive,None,Instant,"Every 9 gives 1m, 10 gives 2m for awareness charms, can lower cost retroactively. Also can use awareness charms in sleep.",267,Awareness,3,1
        |Keen Sight Technique,"3m or 6m, 1wp",Simple,None,Scene,Diff 2 or 3 sight actions don't need a roll. +2 dice to notice hidden things. See clearly in dim light. -1 to fog.,267,Awareness,3,1
        |Unswerving Eye Method,3m,Reflexive,None,Instant,"Against stealth, stealing or cheating, -ess of enemy's double successses after they've rolled.",267,Awareness,4,1
        |Keen Taste and Smell Technique,"3m or 6m, 1wp",Simple,None,Scene,Add scent library and taste index.,268,Awareness,3,1
        |Genius Palate Summation,2m,Simple,None,Instant,Autowin read intentions on chef when they made your food.,269,Awareness,3,1
        |Foe-Scenting Method,0m or 2m,Reflexive,None,Instant,Smell how many people are within medium range. Can tell type of creature.,269,Awareness,4,1
        |Keen Hearing and Touch Technique,"3m or 6m, 1wp",Simple,None,Scene,"Diff 2 sound or touch actions don't need a roll. -1 to harder actions, +1 succ.",269,Awareness,3,1
        |Studied Ear Espial,1m,Reflexive,None,Instant,When someone moves in stealth +3dice to spot them.,269,Awareness,3,1
        |Eyeless Harbinger Awareness,3m,Reflexive,None,Scene,"If stealthed character moves within short range on surface touching you, -2 to their roll.",269,Awareness,3,1
        |Awakening Eye,"5m, 1wp",Supplemental,None,Instant,"For Join Battle, add free full excellency and cascading 10s reroll. Each non-succ that became a succ adds +1succ to be used on awareness in this scene.",270,Awareness,4,1
        |Inner Eye Focus,4m,Reflexive,None,Instant,"Against stealth or larceny, reroll ess non-successes cascading.",270,Awareness,5,2
        |Scent-Honing Prana,3m,Reflexive,None,Instant,"When detecting intimacies, add +1 succ for minor, +2 for major and +3 for defining.",270,Awareness,5,2
        |Knowing Beyond Silence,2m,Reflexive,None,Instant,"Against stealth, enemy's 1s are 10s to you, and their 2s are 9s after they rolled.",270,Awareness,4,2
        |Living Pulse Perception,-,Permanent,None,Permanent,Add +1 succ against hidden character within an enclosed space.,271,Awareness,4,2
        |Roused Dragon Detection,"1m, 1wp",Reflexive,None,Instant,"Hit hidden enemy within range, forces them to move to a new hiding place.",271,Awareness,5,2
        |Unsurpassed Sight Discipline,-,Permanent,None,Permanent,"Any sight-based charm costs 1m less, minimum 1m.",271,Awareness,5,3
        |Blink,1wp,Reflexive,None,Instant,"Reroll any awareness roll, keeping charm effects and allowing new ones.",271,Awareness,5,3
        |Unsurpassed Taste and Smell Discipline,-,Permanent,None,Permanent,Autowin read intention to smell mood. Expand library and index. Smell bleeding characters.,271,Awareness,5,3
        |Unsurpassed Hearing and Touch Discipline,-,Permanent,None,Permanent,Hear loud noises up to 5x ess miles. Also extra techniques.,272,Awareness,5,3
        |Dedicated Unerring Ear,3m/exchange,Reflexive,None,Indefinite,Hear anyone on your plane talking to you. One-way only.,273,Awareness,5,4
        |Eye of the Unconqured Sun,"10m, 1wp",Simple,None,One Turn,I CAN SEE FOREVER.,273,Awareness,5,4
        |Fists of Iron Technique,1m,Reflexive,Dual,Instant,"Parry lethal with hands. Unarmed does lethal, withering ignores (ess+intimacy) soak.",273,Brawl,1,1
        |Iron Battle Focus,3m,Reflexive,None,One Turn,Immune to further onslaught penalties until next action.,273,Brawl,3,1
        |Ferocious Jab,1m,Supplemental,Uniform,Instant,Add your onslaught as damage dice to your attack.,274,Brawl,3,1
        |Wind and Stones Defense,3m,Reflexive,None,Instant,Add enemy's onslaught to your evasion or parry.,274,Brawl,4,1
        |Heaven Thunder Hammer,7m,Reflexive,Decisive-Only,Instant,Knock enemies down or further away depending on succ.,274,Brawl,3,1
        |Vicious Lunge,1m,Supplemental,None,Instant,Add 1 succ to grapple attack and (ess or 3) dice to initiative roll.,275,Brawl,3,1
        |Unbreakable Grasp,2m/round pres.,Reflexive,None,Instant,Pay 2m when hit to conserve grapple control rounds.,275,Brawl,3,1
        |Devil-Strangling Attitude,- (3m),Permanent,None,Instant,Use Strength+Brawl to grapple.,275,Brawl,5,1
        |Crashing Wave Throw,5m,Reflexive,Uniform,Instant,"When ending a grapple to throw, +2 damage per round forfeited. Throw up to short range.",275,Brawl,4,1
        |Thunderclap Rush Attack,3m,Reflexive,None,Instant,Free move and attack. Uses up turn.,275,Brawl,3,1
        |Falling Hammer Strike,1m,Supplemental,Uniform,Instant,Enemy's onslaught is extended and builds until they aren't hit by you for 1 round.,275,Brawl,4,1
        |Reckless Fury Discard,"3m, 1i",Reflexive,Perilous,Instant,"After enemy rolls attack, add their 1s to your parry or evasion. Precedence over charms that reroll 1s.",276,Brawl,4,2
        |Solar Cross-Counter,"3m, 1i, 1wp",Reflexive,"Counterattack, Decisive-Only, Perilous",Instant,"After being hit by a close-range wither, counter with decisive with base damage equal to the damage you took.",276,Brawl,5,2
        |Ox-Stunning Blow,"4m, 1i, 1wp",Simple,Withering-Only,Instant,Add 1 succ to attack and ess dice to damage. Only soaked by stamina.,277,Brawl,3,2
        |Ox-Stunning Blow x2,"4m, 1i, 1wp",Simple,Withering-Only,Instant,Can gain Init on top of penalizing target.,277,Brawl,3,3
        |Burning Fist Burial,4m +1m/die,Reflexive,Decisive-Only,Instant,"Convert extra succ from decisive attack to its damage, 1m each.",277,Brawl,4,2
        |Force-Rending Strike,"5m, 1wp",Reflexive,"Clash, Decisive-Only",Instant,Free clash against a decisive attack.,277,Brawl,4,2
        |Blade-Rebuking Wrath,"4m, 1i",Reflexive,"Clash, Uniform",Instant,Clash to disarm instead of damage.,277,Brawl,5,2
        |Sledgehammer Fist Punch,5m,Supplemental,None,Instant,Add strength succ to a (strength + athletics) attack on an object. Speeds it up and is reflexive if it doesn't directly cause harm.,277,Brawl,4,2
        |Oak-Curling Clinch,2m,Reflexive,None,Instant,Convert extra succ from grapple attack into dice for control roll.,278,Brawl,4,2
        |Burning Proof of Authority,4m,Reflexive,None,Indefinite,Release grapple target with 2 rounds left to mark them. Reflexively grapple them again without a roll to continue the rounds left.,278,Brawl,4,2
        |Hammer on Iron Technique,"5m, 1wp",Simple,Decisive-Only,Instant,Make ((strength or stamina/2) rounded up +1) attacks against a target.,278,Brawl,5,2
        |One With Violence,-,Permanent,None,Permanent,"Add ess initiative, up to 5, to the initiative break bonus for crashing someone with brawl or martial arts.",278,Brawl,5,2
        |Dancing With Strife Technique,-,Reflexive,None,Instant,"If defend against attack with 5+ succ, gain 1 wp. Once per scene.",278,Brawl,5,3
        |Knockout Blow,"5m,1wp,+1m,1i/die",Simple,Withering-Only,Instant,"Double 9s and if they crash they're ko'd. Pay 1m 1i per damage added, up to the initiative gained.",278,Brawl,5,3
        |Cancel the Apocalypse,5m,Reflexive,None,Instant,"Upong crashing a target, deactive one of their ongoing combat charms. Except permanent.",279,Brawl,5,3
        |Adamantine Fists of Battle,4m,Supplemental,Dual,Instant,"When withering unarmed, add strength to overwhelming. For decisive, lethal double 10s and reroll 10s.",279,Brawl,5,3
        |Adamantine Fists of Battle x2,"5m,1wp (2m,1wp)",Simple,Dual,Scene,Can activate scene-long reflexively for altered cost if Supp. version is used to damage opponent.,279,Brawl,5,3
        |Intercepting Fury Smite,-,Permanent,None,Permanent,FRS is 4m and 1wp cheaper with improvised weapons. Drops current weapon.,279,Brawl,5,3
        |Fire-Eating Fist,1m,Supplemental,None,Instant,"On a clash, enemy's 1s give you succ. Punching energy gives buffs.",279,Brawl,5,3
        |River-Binding Wrath,2m or 4m,Supplemental,None,Instant,2m to reroll recurring 5s and 6s on a grapple attack or control. 4m for both.,279,Brawl,5,3
        |Wicked Dissolve Dust,4m,Reflexive,Decisive-Only,Instant,"When controlling a grapple, block attack using victim with a dexterity+brawl clash.",280,Brawl,5,3
        |Rapturous Cradle,"1m, 1wp",Reflexive,None,Instant,May grab target marked by BPoA up to long range.,280,Brawl,5,3
        |Dragon Coil Technique,3m,Reflexive,Uniform,Instant,Add ess succ to grapple attack and enemy's 10s add succ to your control roll.,280,Brawl,5,3
        |Ten Calamities Technique,5m,Supplemental,Dual,Grapple,"Choke out enemy in grapple as savaging attack. Add +2 damage per round for wither, or +1 for decisive, ignoring hardness.",280,Brawl,5,3
        |Titan-Straightening Method,"7m, 1wp",Simple,Withering-Only,Instant,Expend remaining control rounds for equal amount of withering savage attacks. Permanently allows DCT to grapple huge enemies for this charm.,280,Brawl,5,3
        |Shockwave Technique,"6m, 1wp",Reflexive,Withering-Only,Instant,Throw grappled enemy at enemies. +4 damage dice per round left. Knocking them prone.,281,Brawl,5,3
        |Lightning Strikes Twice,"1m, 1wp",Reflexive,None,Instant,Make an extra attack after using CWT or HTH.,281,Brawl,5,3
        |Fivefold Fury Onslaught,-,Permanent,None,Permanent,Upgrades HoIT to use (strength or stamina)+1 attacks and bonus damage to each successful hit +1 then +2 etc.,281,Brawl,5,3
        |Striving Aftershock Method,2m,Reflexive,None,Instant,"After making a decisive attack and resetting to base, add +2 to initiative.",281,Brawl,5,3
        |Superior Violent Knowledge,4m,Reflexive,None,Indefinite,"At any time, store up to stamina initiative. When making a decisive attack, convert the initiative into raw damage.",282,Brawl,5,3
        |Inevitable Victory Meditation,"3m, 2i",Simple,None,Instant,Roll wits+brawl and store the result. Use this result instead of a new brawl roll at any time.,282,Brawl,5,3
        |Incarnated Fury Attack,"10m, 3a, 1wp",Simple,Withering-Only,Instant,"On a crashed target, make an unblockable and undodgeable withering attack with double 7s damage.",282,Brawl,5,4
        |Orichalcum Fists of Battle,"8m,3a,1wp,+1m/turn",Simple,Decisive-Only,Scene,"While using scene-long AFoB, eat anima to make decisive attacks ignore hardness and other bonuses for the scene.",282,Brawl,5,4
        |Raging Wrath Repeated,"4m, 1wp",Reflexive,None,Instant,"After crashing a grapple target, reset combat action and restore all spent control rounds.",282,Brawl,5,4
        |Rampage-Berserker Attack,"7m, 3i, 1wp",Simple,"Perilous, Withering-Only",Instant,"Make a withering brawl attack, with instead of being used for damage, extra successes determine extra attacks and repeated damage.",282,Brawl,5,4
        |Supremacy of War Meditation,-,Reflexive,None,Instant,"Each time reaching iconic anima from dim, use free full brawl excellency.",283,Brawl,5,4
        |Apocalypse Flare Attack,"3m, 2i",Simple,Decisive-Only,Instant,Throw essence with damage equal to damage gathered using FEF. May add 3 anima for 3 succ and ess damage.,283,Brawl,5,5
        |Heaven Fury Smite,-,Reflexive,Decisive-Only,Instant,"After crashing target, reset attack to hit again instantly with a decisive attack against them. Using any combat ability.",283,Brawl,5,5
        |Ascendant Battle Visage,"15m, 3a, 1wp",Reflexive,None,Scene,"Go super saiyan. Rush with double 7s and can use strength instead. Use combat action to clash without delay, moving close to the enemy. Withering can't crash you unless close range. Can reflexively use OFoB cheaper. Store damage from any clash using FEF. May use AFA without spending anima.",283,Brawl,5,5
        |Frugal Merchant Method,1m,Simple,None,Instant,Detect quality of a good.,283,Bureaucr.,1,1
        |Insightful Buyer Technique,3m,Simple,None,Instant,Know how much you can sell a thing for.,284,Bureaucr.,3,1
        |Consumer-Evaluating Glance,3m,Reflexive,None,Instant,Know someone’s budget and whether he plans to cheat you.,284,Bureaucr.,3,1
        |All-Seeing Master Procurer,5m,Reflexive,None,Scene,People can feel how great a merchant you are.,284,Bureaucr.,4,1
        |Illimitable Master Fence,1m,Simple,None,Instant,Know what Bureaucracy specialties the people around you have.,284,Bureaucr.,5,1
        |Deft Official's Way,5m,Reflexive,None,Scene,"Scenelong - add Bureaucracy to a Read Intentions roll, sometimes.",284,Bureaucr.,1,1
        |Measuring Glance,5m,Simple,None,Instant,Unfailingly sense people’s Intimacies towards an organization.,284,Bureaucr.,2,1
        |Enigmatic Bureau Understanding,-,Permanent,None,Permanent,Sense when someone tries to turn a member of an organization against it.,285,Bureaucr.,4,1
        |Speed the Wheels,8m,Simple,None,Task,Accelerate granting bureaucratic requests.,285,Bureaucr.,5,1
        |Bureau-Rectifying Method,"10m, 1wp",Simple,None,Investigation,Scenelong - add Bureaucracy to Socialize and Investigate in a bureau; all members of the bureau trust you.,285,Bureaucr.,5,1
        |Enlightened Discourse Method,4m,Reflexive,None,Scene,Scenelong – add half Bureaucracy to social influence or bureaucratic ends.,285,Bureaucr.,3,1
        |Irresistible Salesman Spirit,"6m, 1wp ",Supplemental,None,Instant,Bargain with double-7s.,285,Bureaucr.,5,2
        |Ungoverned Market Awareness,-,Permanent,None,Permanent,Sense deals being made around you via Bureaucracy or Larceny.,286,Bureaucr.,5,2
        |Bureau-Reforming Kata,"5m, 1wp ",Reflexive,None,Instant,"Remove corruption, with unclear results.",286,Bureaucr.,5,2
        |Indolent Official Charm,5m ,Simple,Stackable,Indefinite,"Induce corruption, slowing bureaucratic process.",286,Bureaucr.,5,2
        |Semantic Argument Technique,1m ,Supplemental,None,Instant,Add half Bureaucracy to social influence to follow The Rewlz.,286,Bureaucr.,5,2
        |Empowered Barter Stance,-,Permanent,None,Permanent,Sell anything to anyone.,286,Bureaucr.,5,3
        |Soul-Snaring Pitch,"5m, 1wp ",Simple,"Mute, Psyche",Instant,Recover willpower for doing Bureaucracy.,286,Bureaucr.,5,3
        |Woe-Capturing Web,-(Varies) ,Permanent,None,Permanent,"Sense magic targeting your organization; stop it, if necessary.",287,Bureaucr.,5,3
        |Omen-Spawning Beast,-,Permanent,None,Permanent,Profile the person using magic on your organization.,287,Bureaucr.,5,3
        |Foul Air of Argument Technique,"13m, 1wp ",Simple,None,Indefinite,Kill a project.,288,Bureaucr.,5,3
        |Eclectic Verbiage of Law,-,Permanent,None,Permanent,"Free bureaucracy Excellency, once per season.",288,Bureaucr.,5,3
        |Infinitely-Efficient Register,-,Permanent,None,Permanent,"Finish a project, without actually undertaking it.",288,Bureaucr.,5,3
        |Taboo-Inflicting Diatribe,"10m, 1wp ",Simple,Stackable,Indefinite,Your bureaucracy may no longer _____.,288,Bureaucr.,5,4
        |Subject-Hailing Ideology,5m ,Supplemental,None,Instant,Compel a person to act in his official capacity.,288,Bureaucr.,5,4
        |Order-Conferring Action,"10m, 1wp ",Simple,None,One week,Enhance an organization’s die pools; make it a bulwark against the Wyld.,289,Bureaucr.,5,5
        |Tireless Workhorse Method,-,Permanent,,Permanent,(2*Essence) free Major slots.,289,Craft,2,1
        |Efficient Craftsman Technique,-,Permanent,None,Permanent,Reduce cost of making a Major slot.,289,Craft,3,1
        |Arete-Shifting Prana,"4m, 1sxp, 1wp ",Simple,None,Instant,Convert dots from one Craft Ability to a related one.,289,Craft,4,1
        |Supreme Celestial Focus,-,Permanent,None,Permanent,Spend gxp to raise a new Craft Ability.,289,Craft,5,2
        |Sublime Transference,6m ,Simple,Mute,Instant,Convert 4sxp <-> 2gxp <-> 1wxp,289,Craft,5,2
        |Ages-Echoing Wisdom,-,Permanent,None,Permanent,Gain (Major Slots) gp at the end of every story.,290,Craft,5,2
        |Dragon Soul Emergence,-,Permanent,Stackable,Permanent,Gain one Superior slot.,290,Craft,5,2
        |Copper Spider Conception,"5m, 1wp ",Simple,None,Instant,Reduce cost of making a Superior slot.,290,Craft,5,3
        |Clay and Breath Practice,-,Permanent,None,Permanent,Earn sxp while making a Superior project.,290,Craft,5,3
        |Spirit-Gathering Industry,-,Permanent,None,Permanent,Finish a Superior project at lower cost.,290,Craft,5,3
        |Summit-Piercing Touch,"10m, 1wp ",Simple,None,Indefinite,Build Superior projects in Major slots.,290,Craft,5,3
        |Vice-Miracle Technique,-,Reflexive,None,Instant,"Once a season, whip out a free 2-dot Artifact.",290,Craft,5,3
        |Unwinding Gyre Meditation,"10m, 1wp ",Reflexive,None,Instant,"Chain the gxp from one Superior project into the next, making it easier and more rewarding.",291,Craft,5,4
        |God-Forge Within,-,Permanent,Stackable,Permanent,Gain two Legendary slots.,291,Craft,5,4
        |Exegesis of the Distilled Form,"25sxp, 15gxp, 10wxp + all remaining wxp ",Simple,None,Instant,Convert all wxp into real experience points.,291,Craft,5,5
        |Spirit-Stoking Elevation,-,Permanent,None,Permanent,Spend wxp 5:1 in place of real experience points for Lore Charms and similar effects.,291,Craft,5,5
        |Sun-Heart Tenacity,-,Permanent,None,Permanent,"On finishing a Legendary project, gain bonus successes to your next Superior/Legendary.",291,Craft,5,5
        |Wonder-Forging Genius,-,Reflexive,None,Instant,"While building 10 Artifacts of the same level simultaneously, immediately finish one of them at no cost.",292,Craft,5,5
        |Dual Magnus Prana,30wxp ,Reflexive,None,Instant,"On death, reveal that the you that died was really a magic-robot-clone, and the real you has been elsewhere the whole time.",292,Craft,5*,5
        |Brass Scales Falling,-,Permanent,None,Permanent,"When you roll for a project with no Excellency, gain sxp for each 10.",292,Craft,3,1
        |Brass Scales Falling x2,-,Permanent,None,Permanent,Increases cap to (Ess * 3),292,Craft,3,1
        |Red Anvils Ringing,-,Permanent,None,Permanent,+1sp per objective on a Basic project,292,Craft,4,1
        |Chains Fall Away,-,Permanent,None,Permanent,+1gp when meeting all objectives on a project,292,Craft,5,1
        |Craftsman Needs No Tools,6m ,Simple,Mute,One task,"Finish a Basic or Major project in moments, with your bare hands.",292,Craft,3,1
        |Peerless Paragon of Craft,-,Permanent,None,Permanent,"Roll dice every story, gain sxp or gxp for roll.",292,Craft,5,1
        |Supreme Perfection of Craft,-,Permanent,None,Permanent,"Gain sxp and gxp for each Craft Ability at 5, once a day.",293,Craft,5,2
        |Thousand-Forge Hands,"10m, 1wp ",Reflexive,None,Instant,Accelerate Superior or Legendary projects to weeks/months.,293,Craft,5,2
        |Divine Transcendence of Craft,-,Permanent,None,Permanent,Gain 3 wxp/story.,294,Craft,5,3
        |Words-as-Workshop Method,"5m, 1wp ",Simple,"Mute, Stackable",Instant,Invent artifact tools out of the air for a temporary purpose.,294,Craft,5,3
        |Shattering Grasp,6m ,Simple,Mute,One task,Break stuff.,294,Craft,5,1
        |Durability-Enhancing Technique,5m ,Simple,None,Instant,Make a mundane object permanently durable.,294,Craft,5,1
        |Crack-Mending Technique,"10m, 1wp ",Simple,None,Instant,Repair a nearly destroyed thing.,295,Craft,3,1
        |Time Heals Nothing,"4m, 1wp ",Simple,None,Instant,Create a major slot for repairs only.,295,Craft,5,1
        |Time Heals Nothing x2,"6m, 1wp",Simple,None,Instant,Create a superior slot for repairs only.,295,Craft,5,3
        |Blood Diamond Sweat,-,Permanent,None,Permanent,Extra craft points for repairs.,295,Craft,5,1
        |Object-Strengthening Touch,6m ,Simple,Mute,Scene,Make an object temporarily nigh-invulnerable.,295,Craft,5,2
        |Chaos-Resistance Preparation,5m ,Simple,None,Instant,An object – and anyone nearby – can resist the Wyld for extended periods.,295,Craft,5,2
        |Breach-Healing Method,7m ,Simple,None,One task,You and allies gain (Essence) dice to rolls using smarty-pants Abilities.,296,Craft,5,2
        |Design Beyond Limit,"10m, 1wp, 3xp ",Simple,None,Instant,Add Evocations to a weapon.,296,Craft,4,2
        |The Art of Permanence,"6m, 1wxp ",Supplemental,None,Instant,"Make an object that lasts forever – an ever-burning candle, say.",296,Craft,5,3
        |Realizing the Form Supernal,"5m, 1wp ",Reflexive,None,Instant,Bonus successes to repair an object.,296,Craft,5,3
        |Celestial Reforging Technique,"10m, 1wp, 3xp ",Simple,None,Instant,Switch up which Evocations in a weapon are live.,296,Craft,5,3
        |,-,Permanent,None,Permanent,Add Evocations to a weapon again.,297,Craft,5,3
        |,"15m, 1wp, 4xp, 4wxp ",Simple,None,Instant,Free Evocations.,297,Craft,5,4
        |Flawless Handiwork Method,6m ,Supplemental,None,Instant,Reroll 10s until they disappear.,297,Craft,1,1
        |Flawless Handiwork Method x2,6m ,Supplemental,None,Instant,Reroll 6s until they fail to appear.,297,Craft,3,1
        |Triumph-Forging Eye,-,Permanent,None,Permanent,Free full Craft Excellency once a week.,297,Craft,2,1
        |Supreme Masterwork Focus,6m ,Supplemental,None,Instant,Basic and Major projects get double 9s.,297,Craft,3,1
        |Supreme Masterwork Focus x2,"5m, 1wp, 1gxp",Supplemental,None,Instant,"Basic, Major, or Superior get double 8s.",297,Craft,5,2
        |Supreme Masterwork Focus x3,"2m, 1wxp",Supplemental,None,Instant,Any Craft roll gets double 7s.,297,Craft,5,3
        |Bright-Forging Prana,-,Reflexive,None,Instant,Act as if you had three more Craft Charms than you did; you can switch up which ones.,297,Craft,3,2
        |Experiential Conjuring of True Void,"4m, 4s/g/wxp ",Reflexive,Salient,Instant,Bonus non-Charm successes/dice on non-Basic projects. (Why both?),298,Craft,3,2
        |Unbroken Image Focus,3m + 1s/g/wxp/success ,Reflexive,Salient,Instant,Spend craft points for bonus non-Charm successes.,298,Craft,4,2
        |First Movement of the Demiurge,-,Permanent,None,Permanent,"Convert non-successes to 10s, for every three of a kind success you roll.",298,Craft,4,2
        |Essence-Forging Kata,"2m/mote, 1wp ",Simple,None,One day,"Infinite Craft Mastery. Like, literally, exactly that, except daylong.",298,Craft,5,3
        |Mind-Expanding Meditation,"1sxp/cap increase, 1wxp ",Reflexive,None,Instant,Spend sxp to raise the limit of your Excellency.,298,Craft,5,3
        |Inspiration-Renewing Vision,"12m, 1wp, 2wxp ",Supplemental,None,Instant,Roll once for a Superior or Legendary project; this doesn’t count against the maximum allowed rolls.,298,Craft,5,3
        |Divine Inspiration Technique,-,Permanent,None,Permanent,Roll an extra die for every three successes on a Craft roll.,299,Craft,5,3
        |Horizon-Unveiling Insight,-,Permanent,None,Permanent,Get an extra roll before the end of Superior/Legendary projects.,299,Craft,5,4
        |Holistic Miracle Understanding,-,Permanent,None,Permanent,"If DIT produces 3+ more successes, add 3 more dice.",299,Craft,5,4
        |Reed in the Wind,2i/1 Evasion ,Reflexive,Perilous,Instant,"Spend Initiative to raise Dodge, breaking the cap.",299,Dodge,3,1
        |Dust Motes Whirling,2m ,Supplemental,None,Instant,Disengage with double-9s.,299,Dodge,4,1
        |Shadow Dancer Method,-(1m) ,Permanent,None,Permanent,"Keep the Initiative for Disengaging (or even earn it back, if you close again).",299,Dodge,5,1
        |Reflex Sidestep Technique,5m ,Reflexive,Uniform,Instant,Have Evasion 2+ when ambushed.,299,Dodge,3,1
        |Leaping Dodge Method,"1m, 2i ",Reflexive,None,Instant,"After dodging an effect, move one range band.",300,Dodge,3,1
        |Searing Quicksilver Flight,-,Permanent,None,Permanent,"When you dodge, the enemy loses 1i.",300,Dodge,4,1
        |Drifting Leaf Elusion,1m ,Reflexive,None,Instant,"Your Evasion wins ties, rather than losing them.",300,Dodge,2,1
        |Shadow Over Water,2m ,Reflexive,None,Instant,Remove any penalties to Evasion.,300,Dodge,3,1
        |Fleet Dreaming Image,5m ,Reflexive,None,Instant,"Disengage while at short range, rather than close.",300,Dodge,5,2
        |Drifting Shadow Focus,"3m, 1wp ",Reflexive,None,One turn,"On a successful dodge, redirect the attack to anyone at close range.",300,Dodge,4,2
        |Force-Stealing Feint,-,Permanent,None,Permanent,Gain the 1i lost by the opponent from SQF.,300,Dodge,5,2
        |Seven Shadow Evasion,"4m, 1wp ",Reflexive,Uniform,Instant,Perfectly dodge any attack.,300,Dodge,5,2
        |Safety Between Heartbeats,5m ,Reflexive,None,Instant,Opponent loses 1i for each 1 rolled on the attack.,301,Dodge,5,2
        |Fourfold Shiver Binding,4m ,Reflexive,None,Scene,+1 non-Charm bonus to Evasion for rest of scene.,301,Dodge,4,2
        |Flow Like Blood,"5m, 1wp ",Reflexive,Perilous,Scene,Ignore all Evasion penalties against enemies with lower Initiative. Cut cost of RitW in half. Gain 1i each round you are at close range and not struck.,301,Dodge,4,2
        |Rumor of Form,3m/-1 ,Reflexive,None,Instant,Convert enemy 1s into -1 successes.,301,Dodge,5,2
        |Way of Whispers Technique,-,Permanent,None,Permanent,DLE and RoF don’t count as Charms for the purpose of UWM.,302,Dodge,5,2
        |Vaporous Division,4m/damage removed ,Reflexive,Decisive-only,Instant,Cancel damage at 4m/success.,302,Dodge,5,2
        |Sunlight Bleeding Away,"4m, 1wp ",Reflexive,None,Instant,Retreat twice after disengaging instead of once.,302,Dodge,5,3
        |Thousand Steps’ Stillness,5m ,Reflexive,None,Instant,"On a successful dodge, gain 1i per 1 or 2 enemy rolls.",302,Dodge,5,3
        |Unbowed Willow Meditation,-,Permanent ,None,Permanent,"On a successful dodge without Charms, crash the enemy and steal his Initiative.",302,Dodge,5,3
        |Hundred Shadow Ways,6m ,Reflexive,Stackable,Scene,Learn an enemy Charm and become immune to its effects.,302,Dodge,5,3
        |Living Bonds Unburdened,"3m, 3i +1m, 1i/hl ",Simple,None,Scene,"Create extra -0 health levels; if hit by an attack that only damages these levels, you count as dodging it.",303,Dodge,5,3
        |Unbridled Shade Attitude,-,Permanent,None,Permanent,Gain 1i for every -0 health level you lose.,303,Dodge,5,3
        |Harm-Dismissing Meditation,"1m, 1wp ",Simple,None,One turn,"Stand still for a round, and heal health levels.",303,Dodge,5,3
        |Refinement of Flowing Shadows,-,Permanent,Perilous,Instant,"After using SSE, gain 1i/round until you are struck or move too far away.",303,Dodge,5,5
        |Enduring Mental Toughness,1m ,Reflexive,Bridge,Instant,Ignore penalties from injuries and other harm to your Resolve and Guile.,303,Integrity,1,1
        |Enduring Mental Toughness x2,"5m, 1wp",Reflexive,Bridge,Scene,Ignore penalties from injuries and other harm to your Resolve and Guile.,303,Integrity,3,1
        |Enduring Mental Toughness x3,"8m, 1wp",Reflexive,Bridge,Day,Ignore penalties from injuries and other harm to your Resolve and Guile.,303,Integrity,5,3
        |Stubborn Boar Defense,-,Permanent,Bridge,Permanent,Gain +2 Resolve against issues you’ve resisted in the past.,303,Integrity,2,1
        |Integrity-Protecting Prana,"-(5m, 1wp) ",Permanent,None,Permanent,Resist the natural twisting power of the Charm.,304,Integrity,3,1
        |Destiny-Manifesting Method,"-(3m, 1wp) ",Permanent,Stackable,Permanent,"Magic can alter you, but it cannot immediately render you unable to be who you are. (“Turn into a duck” becomes “Become duck-ish, but continue to daiklave things just fine; also, you can break this curse.”)",304,Integrity,1,1
        |Legend-Soul Revival,-,Permanent,None,Permanent,Become immune to any curse you’ve broken via DMM.,305,Integrity,3,1
        |Spirit-Maintaining Maneuver,5m/1wp ,Reflexive,None,Instant,Spend 5m for 1wp when resisting social influence or magic.,305,Integrity,3,2
        |Undying Solar Resolve,-,Permanent,Bridge,Permanent,Gain 1m for every 1 or 2 rolled on social influence or curses.,305,Integrity,4,2
        |Temptation-Resisting Stance,"5m, 1wp ",Simple,None,Scene,+1 Resolve when defending with Ties/Principles. Effect stacks with multiple Solars.,306,Integrity,3,2
        |Mind-Cleansing Prana,5m ,Simple,"Bridge, Mute",Instant,Meditate for an hour; gain +2 successes on a concentration-based roll.,306,Integrity,4,2
        |Clear Mind Discipline,-,Reflexive,Bridge,Instant,1/day MCP only takes seconds.,306,Integrity,5,2
        |Energy Restoration Prana,-,Reflexive,Bridge,Instant,"Treat a use of MCP as a full night’s sleep, also restoring 20m and resetting 1/day Charms.",306,Integrity,5,2
        |Steel Heart Stance,"4m, 1wp ",Reflexive,Bridge,Instant,Reject influence even after being persuaded.,306,Integrity,4,2
        |Righteous Lion Defense,-,Permanent,Bridge,Permanent,Make one of your Principles (nearly) inviolable.,307,Integrity,5,2
        |Watchful Eyes of Heaven,3m ,Reflexive,None,Instant,Sense when someone nearby is considering acting against a Principle you like.,307,Integrity,5,2
        |Accord of the Unbreakable Spirit,6m ,Simple,Bridge,One hour,"Pray with your followers for an hour; for the duration, they can resist the Wyld, disease, and hardship.",307,Integrity,5,2
        |Phoenix Renewal Tactic,-(Varies) ,Permanent,None,Permanent,Use your Willpower to resist an attack with no clear means of defense.,307,Integrity,5,2
        |Phoenix Renewal Tactic x2,-(Varies) ,Permanent,None,Permanent,"Grants 1 non-Charm succ, can convert 2wp dice to 3 succ.",307,Integrity,5,2
        |Sun King Radiance,-,Permanent,None,Permanent,Your followers gain Intimacies and bonus dice when they see you succeed socially.,308,Integrity,5,2
        |Soul-Nourishing Technique,"4m, 1wp ",Simple,None,Instant,Your sermons feed your followers.,308,Integrity,5,2
        |Spirit-Tempering Practice,-,Reflexive,None,Instant,"Outside combat, turn motes into Willpower.",308,Integrity,5,3
        |Empowered Soul Technique,-,Supplemental,Bridge,Instant,"1/day, raise your Resolve at 1m/point instead of 2m/point.",308,Integrity,5,3
        |Transcendent Hero’s Meditation,"7m, 1wp ",Simple,None,Instant,"After being forced to act against an Intimacy, shatter the effect controlling you.",308,Integrity,5,3
        |Righteous Soul Judgment,-,Reflexive,None,Instant,"When your Intimacies are challenged, ignore the cost of three Charms to resist harm or influence.",309,Integrity,5,3
        |Barque of Transcendent Vision,-,Permanent,Bridge,Instant,"While using MCP, you cannot be influenced, but you also can’t react to others.",309,Integrity,5,3
        |Unhesitating Dedication,3m ,Reflexive,None,Instant,Steal successes from your enemy’s social influence.,309,Integrity,5,3
        |Invincible Solar Aegis,"10m, 1wp ",Reflexive,Bridge,Indefinite,Use a Defining Principle to shake off major contradictory magical effects.,309,Integrity,5,3
        |Eminent Paragon Approach,1m ,Supplemental,Bridge,Instant,Everyone around you realizes you have a particular Defining Principle.,310,Integrity,5,3
        |Divine Mantle,"7m, 1wp ",Reflexive,Bridge,Instant,"Learn a bunch of non-Eclipse-tagged Spirit Charms, as long as they fit your Principles and Charms.",310,Integrity,5,3
        |Body-Restoring Benison,-,Permanent,Bridge,Permanent,"When using MCP, heal and avoid the need for food.",310,Integrity,5,4
        |Inviolable Essence-Merging,7m ,Reflexive,None,Indefinite,"While using MCP, become immune to harm.",311,Integrity,5,4
        |Watchman’s Infallible Eye,-,Reflexive,None,Instant,Auto-detect “There’s something fishy here; roll to try to notice it.” Nice!,311,Investig.,1,1""" ,
    """
        |Inquisitor’s Unfailing Notice,-,Permanent,None,Permanent,Auto-detect “Larceny is being used nearby.”,311,Investig.,2,1
        |Crafty Observation Method,5m ,Simple,Mute,Instant,"Case a scene in seconds, with bonuses.",312,Investig.,3,1
        |Divine Induction Technique,-,Permanent,None,Permanent,"1/scene, free full Excellency.",312,Investig.,4,1
        |Evidence-Discerning Method,"2m, 1wp ",Simple,None,Instant,Profile criminal based on crime scene; auto-detect characters matching profile.,312,Investig.,5,1
        |Judge’s Ear Technique,3m ,Reflexive,None,Instant,Tell whether one statement is true or false.,312,Investig.,4,1
        |Miraculous Stunning Insight,-,Permanent,Mute,Permanent,"1/scene, roll Investigation with double-8s.",312,Investig.,5,2
        |Watchful Justiciar’s Eye,"-(4m, 1wp) ",Reflexive,None,Instant,Get an alert (and an explanation) when a character acts against your profile of him.,312,Investig.,5,2
        |Irresistible Questioning Technique,"5m, 1wp ",Supplemental,Mute,Instant,Compel a character to honestly answer questions.,313,Investig.,5,2
        |Dauntless Inquisitor Attitude,6m ,Reflexive,Mute,Scene,"Infinite Investigation Mastery, roughly.",313,Investig.,5,3
        |Evidence-Restoring Prana,-(6m) ,Reflexive,None,Varies,Momentarily restore destroyed evidence.,313,Investig.,5,3
        |Ten Magistrate Eyes,3m ,Supplemental,Mute,Instant,Auto-succeed on a case scene action. Make Holmes-style logical connections between objects and events.,313,Investig.,5,3
        |Unknown Wisdom Epiphany,"10m, 1wp ",Simple,None,Instant,Reenact the events of the crime from the perspective of one of the participants.,313,Investig.,5,3
        |Enlightened Touch Insight,5m ,Simple,None,Instant,Sense the emotions of the person who left behind a piece of evidence.,314,Investig.,5,3
        |Judge-General’s Stance,10m ,Reflexive,None,Instant,"Reset any Investigation Charms with limited use frequency (DIT, MSI, IQT, and MMM).",314,Investig.,5,4
        |Empathic Recall Discipline,"1m, 1wp ",Reflexive,None,Instant,"Understand the perpetrator’s motivations, as per read intentions.",315,Investig.,5,4
        |Mind Manse Meditation,"12m, 1wp ",Simple,None,Instant,Enter your “Mind Manse” and solve some piece of the mystery.,315,Investig.,5,5
        |Seasoned Criminal Method,-,Permanent,None,Permanent,"You can attract criminals, avoid the attention of magistrates, or appear to be involved in any crimes in the area.",315,Larceny,1,1
        |Spurious Presence,6m ,Reflexive,None,Scene,"Appear to belong, wherever you are; boost your Guile to resist detection.",315,Larceny,2,1
        |Preying on Uncertainty Approach,3m ,Reflexive,None,Instant,Treat another’s uncertainty as a Minor Intimacy.,316,Larceny,3,1
        |Clever Bandit’s Rook,2m ,Supplemental,None,Instant,Claim ownership of a thing.,316,Larceny,3,1
        |Swift Gambler’s Eye,1m ,Supplemental,None,Instant,Read Intentions better against someone while gambling.,316,Larceny,3,1
        |Lightning-Hand Sleight,3m ,Reflexive,None,Instant,Cheat at a board game or poison a drink.,316,Larceny,4,1
        |Flawless Pickpocketing Technique,3m ,Supplemental,Mute,Instant,Infallibly steal a thing.,316,Larceny,2,1
        |Lock-Opening Touch,1m or 5m ,Reflexive,None,Instant,Automatically pick a lock.,317,Larceny,2,1
        |Flawlessly Impenetrable Disguise,6m ,Simple,None,Until the Exalt sleeps,Create a perfect disguise.,317,Larceny,4,1
        |Phantom Hood Technique,-,Reflexive,None,Instant,"1/scene, mute five motes.",317,Larceny,3,2
        |Doubt-Sealing Heist,5m ,Reflexive,None,Instant,Convince a target you’ve always owned something you steal from him.,317,Larceny,4,2
        |Living Shadow Preparedness,"4m, 1wp ",Reflexive,None,Instant,Bank successes for future Larceny or Stealth actions.,317,Larceny,3,2
        |Unshakable Rogue’s Spirit,-1 Initiative/success ,Reflexive,None,Instant,"Add successes to a Larceny or Stealth roll, at the cost of future Initiative.",318,Larceny,4,2
        |Master Plan Meditation,"6m, 1wp ",Simple,None,Indefinite,Build points to spend on retroactive(?) larceny actions.,318,Larceny,5,2
        |Proof-Eating Palm,1m ,Reflexive,None,Instant,Perfectly palm an object.,318,Larceny,4,2
        |Stealing From Plain Sight Spirit,6m ,Supplemental,Mute,Instant,"Steal someone’s pants. Or, well, something else in plain sight, but probably pants.",319,Larceny,4,2
        |Magpie’s Invisible Talon,1wp ,Supplemental,None,Instant,"Steal from several yards away, even with obstacles in the way.",319,Larceny,5,2
        |Perfect Mirror,"-(5m, 1wp) ",Permanent,Mute,Permanent,"Pretend to be another person, including special effects.",319,Larceny,5,2
        |Fate-Shifting Solar Arete,1wp ,Reflexive,None,Instant,Treat some number from 2-5 as a 10.,320,Larceny,5,3
        |Fate-Shifting Solar Arete x2,1wp ,Reflexive,None,Instant,Can treat 2 numbers from 2-5 as 10s.,320,Larceny,5,4
        |Skillful Reappropriation (Phantom Sting Search),6m ,Supplemental,None,Instant,Plant evidence on a target.,320,Larceny,5,3
        |Reversal of Fortune,4m ,Reflexive,None,Instant,Steal from someone trying to pickpocket you.,320,Larceny,4,3
        |Iron Wolves’ Grasp,"3m, 4i ",Supplemental,None,Instant,Steal your enemy’s weapon mid fight.,320,Larceny,5,3
        |Door-Evading Technique,"10m, 1wp ",Reflexive,None,Instant,"Teleport through a door. But, like, skillfully, not with magic.",321,Larceny,4,3
        |Flashing Ruse Prana,"10m, 1wp ",Supplemental,Mute,Instant,Adopt a disguise instantly.,321,Larceny,5,3
        |Split Deception Method,5m/disguise ,Reflexive,None,Indefinite,Wear two disguises at once.,321,Larceny,5,3
        |Null Anima Gloves,"12m, 1wp ",Reflexive,None,Indefinite,"Prevent criminals and creatures of darkness from attacking you, briefly; enable a number of weird theft possibilities (dreams, Initiative, etc.).",321,Larceny,5,4
        |Night’s Eye Meditation,5m ,Reflexive,None,One turn,Make your disguises “retroactively harder to pierce.”,322,Larceny,5,5
        |Unbroken Darkness Approach,-,Reflexive,Mute,Instant,"1/scene, free full Larceny Excellency.",322,Larceny,5,5
        |Whirling Brush Method,3m ,Reflexive,"Mute, Written-only",Instant,Copy a book in seconds.,322,Linguistics,1,1
        |Flawless Brush Discipline,1m ,Supplemental,Written-only,Instant,Forge handwriting; render a perfect copy.,323,Linguistics,3,1
        |Letter-Within-A-Letter Technique,4m ,Simple,"Mute, Written-only",Instant,Hide a message to a specific individual in another written message.,323,Linguistics,3,1
        |Subtle Speech Method,3m ,Simple,Mute,One idea conveyed,"As LWALT, but spoken instead of written.",323,Linguistics,3,1
        |Flowing Elegant Hand,2m ,Supplemental,"Mute, Written-only",Instant,Double 9s on written works.,323,Linguistics,3,1
        |Flowing Elegant Hand x2,3m,Supplemental,"Mute, Written-only",Instant,Double 8s on written works.,323,Linguistics,5,3
        |Flowing Elegant Hand x3,4m,Supplemental,"Mute, Written-only",Instant,Double 7s on written works.,323,Linguistics,5,5
        |Strange Tongue Understanding,1m ,Reflexive,None,Instant,"Interpret one statement in a foreign language, or parse “abstruse language.”",324,Linguistics,3,1
        |Poetic Expression Style,1m ,Supplemental,None,Instant,Communicate via body language.,324,Linguistics,4,1
        |Mingled Tongue Technique,"4m, 1wp, plus 3m/language ",Reflexive,None,Scene,Fuse two languages into a new language that speakers of either tongue can understand.,324,Linguistics,5,1
        |Sagacious Reading of Intent,"4m, 1wp ",Reflexive,None,Instant,Automatically sense the Intimacies behind a written work.,324,Linguistics,4,1
        |Word-Shield Invocation,2m ,Reflexive,None,Instant,"After using SRoI, raise Resolve by Linguistics/2.",324,Linguistics,5,1
        |Stolen Voice Technique,3m ,Simple,Written-only,Instant,Write in the style of another person.,324,Linguistics,4,2
        |Moving the Unseen Hand,4m ,Simple,"Mute, Written-only",Instant,"Write an anonymous letter, which readers will assume was written by a specific person.",325,Linguistics,5,2
        |Essence-Laden Missive,1m ,Supplemental,"Mute, Psyche, Written-only",Instant,Compel the reader to announce the contents of the message.,325,Linguistics,4,2
        |Voice-Caging Calligraphy,"2m, 1wp ",Supplemental,"Mute, Psyche, Written-only",Instant,Prevent the reader from repeating the contents of the letter.,325,Linguistics,5,2
        |Single Voice Kata,"5m, 1wp ",Reflexive,None,Scene,Speak a language that everyone can understand.,325,Linguistics,5,2
        |Vanishing Immersion Style,1m ,Reflexive,None,Instant,The character’s Guile cannot be pierced while she’s reading.,326,Linguistics,5,2
        |Discerning Savant’s Eye,"1m, 1wp ",Reflexive,None,Scene,Read faded scripts.,326,Linguistics,5,2
        |Power-Snaring Image,"-(1m, 1wp) ",Permanent,Written-only,Instant,Copy any magic embedded in a document.,326,Linguistics,5,3
        |Flashing Quill Atemi,1m ,Reflexive,Written-only,One minute,Edit a document without trace.,326,Linguistics,5,3
        |Mind-Swallowing Missive,"8m, 1wp ",Supplemental,"Psyche, Written-only",Indefinite,Target forgets all known languages.,326,Linguistics,5,3
        |Cup Boils Over,1m ,Simple,Written-only,Instant,Kill someone who has no sensible Intimacies.,327,Linguistics,5,3
        |Twisted Words Technique,"1m, 1wp ",Simple,Psyche,Instant,Convince someone of the truth of an assertion – but then have them do the opposite of that.,327,Linguistics,5,3
        |Excellent Emissary’s Tongue,6m ,Reflexive,None,Indefinite,Learn a new language.,328,Linguistics,5,3
        |Perfect Recollection Discipline,1m/work ,Reflexive,None,Indefinite,Maintain a perfect memory of a written work.,328,Linguistics,5,3
        |Swift Sage’s Eye,1m ,Reflexive,Mute,One text,Read a book at superhuman speed.,328,Linguistics,5,3
        |Mind-Scribing Method,"5m, 1wp ",Simple,Mute,Instant,Write a book in your miiiiiiiiiiind.,329,Linguistics,5,3
        |Heaven-Drawing Discipline,"8m, 1wp ",Supplemental,Mute,Instant,"Reduce time required to compose a work, though not the time to write it.",329,Linguistics,5,3
        |Heaven-Drawing Discipline x2,"12m, 1wp",Supplemental,Mute,Instant,"Reduce time required by two units to compose, but not write.",329,Linguistics,5,4
        |Heaven-Drawing Discipline x3,"15m, 1wp",Supplemental,Mute,Instant,"Reduce time required by three units to compose, but not write.",329,Linguistics,5,5
        |Perfect Celestial Author,-,Permanent,Written-only,Permanent,Combine Simple Charms on a single work.,329,Linguistics,5,4
        |Unbreakable Fascination Method,"6m, 1wp ",Simple,Psyche,Instant,Hold people spellbound as long as you speak (or as long as they read what you have written).,329,Linguistics,5,4
        |Wyld-Dispelling Prana,"5m, 1wp ",Reflexive,None,Varies,Protect an object or character from Wyld-twisting.,330,Lore,2,1
        |Chaos-Repelling Pattern,"10m, 1wp ",Simple,None,One hour,Characters within short range are protected from the Wyld.,330,Lore,3,1
        |Harmonious Academic Methodology,-,Permanent,None,Permanent,"Get ALL the Lore Specialties, and get bonuses when they apply.",330,Lore,3,1
        |First Knowledge’s Grace,4m ,Reflexive,None,Scene,"Teach the unteachable. Row row, fight da powa.",330,Lore,3,1
        |Flowing Mind Prana,1xp+ ,Simple,None,Instant,Spend your XP to train someone else. Roll XP as dice at end of story; recover successes.,331,Lore,5,1
        |Essence-Lending Method,3m ,Simple,None,Instant,Transfer motes to an ally.,331,Lore,1,1
        |Will-Bolstering Method,"5m, 1wp ",Simple,None,Instant,Transfer Willpower to an ally.,331,Lore,2,1
        |Hidden Wisdom Bestowal,"10m, 1wp ",Supplemental,Mute,Scene,Hide one lesson inside another.,331,Lore,5,2
        |Tireless Learner Method,1xp ,Reflexive,None,Instant,Reroll nonsuccesses when using FMP.,331,Lore,5,2
        |Bottomless Wellspring Approach,-,Permanent,None,Permanent,Gain bonuses when introducing/challenging facts.,332,Lore,4,2
        |Lore-Inducing Concentration,3m ,Supplemental,None,Instant,Reroll 6s when introducing/challenging facts.,332,Lore,5,2
        |Truth-Rendering Gaze,6m ,Simple,Mute,Instant,Reveal the function of an object.,332,Lore,5,2
        |Wound-Accepting Technique,"3m/health level, 1wp ",Simple,None,Instant,Accept wounds from an ally.,332,Lore,4,2
        |Essence Font Technique,-,Permanent,None,Permanent,"Make ELM and WBM Reflexive, with bonuses.",332,Lore,4,2
        |Legendary Scholar’s Curriculum,-,Permanent,None,Permanent,Use FMP to train many people at once.,332,Lore,5,3
        |Selfsame Master Instructor,-,Permanent,None,Permanent,Use FMP to teach spells or Charms.,333,Lore,5,3
        |Sacred Relic Understanding,"4m, 1wp ",Simple,None,Instant,Receive a vision of how to use an artifact.,333,Lore,5,3
        |Wake the Sleeper,"15m, 1wp ",Simple,None,Instant,Gain Evocations for free.,333,Lore,5,3
        |Heaven-Turning Calculations,-,Permanent,None,Permanent,"Add non-Charm dice to thinky problems, permanently.",333,Lore,5,3
        |Injury-Forcing Technique,"5m/health level, 1wp ",Simple,Decisive-only,Instant,Push wounds onto a target.,333,Lore,5,3
        |Essence-Draining Touch,1wp ,Simple,None,Instant,Drain motes from the target.,333,Lore,5,3
        |Essence-Twining Method,"6m, 1wp ",Simple,Mute,Instant,Convert the target’s personal motes to peripheral or vice-versa.,334,Lore,5,3
        |Force-Draining Whisper,"10m, 1wp ",Reflexive,"Clash, Decisive-only",Instant,Steal damage from an attack through your mystic mumblings.,334,Lore,5,3
        |Immanent Solar Glory,-,Permanent,None,Permanent,Regain 1m when you roll a 10 on a Lore roll.,334,Lore,5,3
        |Flowing Essence Conversion,"10m, 3a",Reflexive,Mute,Instant,Reset your anima to dim.,334,Lore,5,3
        |Power-Restoring Invocation,"2i, 3a",Simple,Perilous,Instant,"Burn out your anima, restoring motes to allies.",334,Lore,5,3
        |Surging Essence Flow,-,Permanent,None,Permanent,Allow ELM and WBM to be used at range.,334,Lore,5,3
        |Order-Affirming Blow,"15m, 1wp ",Simple,None,Instant,Destroy Wyld-twisting effects.,334,Lore,5,3
        |Wyld-Shaping Technique,"15m, 1wp, 2xp ",Simple,None,Instant,TEAR NEW REALITY FROM THE LAUGHING TEETH OF MADNESS.,335,Lore,5,3
        |Hero-Induction Method,-,Permanent,None,Permanent,"When using WST, create specialist workers.",337,Lore,5,3
        |Wyld Cauldron Mastery,-,Permanent,None,Permanent,"When using WST, gain bonus successes.",338,Lore,5,3
        |Wyld-Called Weapon,"7m, 1wp, 8xp ",Reflexive,None,Instant,Make artifact weapons using WST.,338,Lore,5,3
        |Wyld-Forging Focus,-,Permanent,None,Permanent,Skip a roll when using WST.,338,Lore,5,3
        |Tome-Rearing Gesture,"5m, 1wp ",Simple,None,Instant,Make a book you thought up using Mind-Scribing Method.,338,Lore,5*,3
        |Power-Awarding Prana,5m + 1m/1xp ,Simple,"Mute, Stackable",Indefinite,Grant Charms you don’t know to a character (maybe you!).,338,Lore,5,4
        |Prophet of Seventeen Cycles,"12m, 1wp ",Simple,None,Instant,"Predict the future, granting bonuses to those who follow your predictions and penalties to those who don’t.",339,Lore,5,4
        |Will-Shattering Illusion,10m ,Simple,Mute,Instant,Steal Willpower and Initiative by posing a riddle.,339,Lore,5,4
        |Surging Inner Fire,-,Simple,None,Instant,Recover extra motes.,339,Lore,5,4
        |Seal of Infinite Wisdom,-,Simple,None,Instant,"Boost the effect of ISG to 8s, 9s, and 10s for one roll.",340,Lore,5,4
        |Sevenfold Savant Mantle,-,Permanent,None,Permanent,Reduce cost of one use of WST.,340,Lore,5,4
        |Power Beyond Reason,-,Permanent,None,Permanent,Gain double-8s on one use of WST.,340,Lore,5,4
        |Power Beyond Reason x2,-,Permanent,None,Permanent,Can use again on another phase to get double 7s.,340,Lore,5,5
        |Manse-Raising Method,"10m, 1wp, 10xp ",Reflexive,None,Instant,Build a manse using WST.,340,Lore,5,4
        |Demiurgic Suspiration,-,Permanent,None,Permanent,Regenerate motes while using WST.,340,Lore,5,4
        |God-King’s Shrike (Dogstar Ruminations),"30m, 1wp ",Simple,None,Instant,"Drop a nuke on an enemy nation from your basement. Yes, really.",340,Lore,5,5
        |Incalculable Flowing Mind,7m ,Reflexive,None,Instant,Gain 3wp.,341,Lore,5,5
        |Unstoppable Magnus Approach,"5m, 10i ",Reflexive,Perilous,Instant,Trade motes and Initiative for Willpower.,341,Lore,5,5
        |Savant of Nine Glories,-,Permanent,None,Permanent,Reduce cost of WST.,341,Lore,5,5
        |Ailment-Rectifying Method,3m ,Simple,None,Instant,Grant target bonuses to resist disease.,341,Medicine,1,1
        |Plague-Banishing Incitation,"2m, 1wp ",Reflexive,None,Instant,Cure disease immediately.,341,Medicine,3,1
        |Wound-Mending Care Technique,"5m, 1wp ",Simple,None,Instant,Restore health levels immediately.,342,Medicine,3,1
        |Wound-Cleansing Meditation,10m ,Simple,None,Instant,Convert target’s aggravated damage to lethal.,342,Medicine,3,1
        |Flawless Diagnosis Technique,1m ,Supplemental,None,Instant,Perfectly diagnose an illness.,342,Medicine,1,1
        |Contagion-Curing Touch,-,Permanent,None,Permanent,"Gain bonuses to treat illness, permanently.",342,Medicine,4,2
        |Instant Treatment Methodology,"5m, 1wp or 5i, 1wp ",Reflexive,None,Instant,Perform an hour of treatment in seconds.,342,Medicine,4,2
        |Wound-Banishing Strike,5m ,Simple,Decisive-only,Instant,Remove a magical crippling injury mid-combat.,342,Medicine,5,2
        |Touch of Blissful Release,5m ,Reflexive,None,Instant,"Negate the target’s wound penalties, though target cannot be active.",343,Medicine,3,2
        |Feit of Imparted Nature,10m ,Simple,None,Indefinite,Grant temporary -0 health levels.,343,Medicine,4,2
        |Body-Purifying Admonitions,"4m, 1wp ",Reflexive,None,Instant,Cause a poison to disperse after its next interval.,344,Medicine,5,2
        |Anointment of Miraculous Health,10m ,Simple,None,Instant,"Convert aggravated damage, or cure bashing/lethal, instantly.",344,Medicine,5,3
        |Body-Sculpting Essence Method,"10m, 1wp ",Reflexive,None,Instant,"As ITM, but the patient also recovers on their feet in seconds.",344,Medicine,5,3
        |Wholeness-Restoring Meditation,"10m, 1wp ",Simple,None,Instant,"Restore missing limbs, regrow eyes, or treat madness.",344,Medicine,5,3
        |Healing Trance Meditation,-,Simple,None,Instant,"Regenerate motes and willpower, and continue to do while making Medicine rolls.",344,Medicine,5,3
        |Life-Exchanging Prana,1lhl/3m ,Reflexive,None,Instant,Spend health levels for motes for Medicine Charms.,345,Medicine,5,3
        |Anodyne of Celestial Dreaming,"7m, 1wp ",Reflexive,None,Scene,"Remove target’s wound penalties; if target is active, doctor takes -1 penalties.",345,Medicine,5,3
        |Master Chirurgeon Meditation,-,Permanent,None,Permanent,Medicine Excellency is permanently 1m/2 dice.,345,Medicine,5,4
        |Benison of Celestial Healing,-,Permanent,None,Permanent,Use a Medicine Charm without paying its cost.,345,Medicine,5,4
        |Life-Sculpting Hands Technique,-,Permanent,None,Permanent,1/scene free full Excellency.,345,Medicine,5,4
        |Healer’s Unerring Hands,"5m, 1wp ",Reflexive,Mute,Instant,Reroll non-successes in Medicine.,345,Medicine,5,4
        |Immaculate Solar Physician,-,Permanent,None,Permanent,"1/day, double Medicine successes.",345,Medicine,5,5
        |Perfect Celestial Chirurgeon,-,Permanent,None,Permanent,"1/story, double-7s on a Medicine roll.",346,Medicine,5,5
        |Excellent Strike,3m ,Supplemental,Uniform,Instant,Gain a success and recurring 1s.,346,Melee,2,1
        |Fire and Stones Strike,1m/die or success ,Supplemental,Dual,Instant,Add post-soak damage or transfer extra attack successes to a Decisive damage roll.,346,Melee,3,1
        |"One Weapon, Two Blows",3m ,Reflexive,None,Instant,"After reducing an enemy from Initiative above yours to Init below yours, make a Reflexive attack.",346,Melee,2,1
        |Peony Blossom Technique,"1m, 1wp, 3a ",Reflexive,None,Instant,Spend your anima to make an extra attack.,346,Melee,3,1
        |Dipping Swallow Defense,2m ,Reflexive,None,Instant,Ignore Defense penalties; gain 1i for parrying.,346,Melee,1,1
        |Bulwark Stance,5m ,Reflexive,None,Until next turn,Ignore Defense penalties for one turn; opponent does reduced damage.,346,Melee,3,1
        |War Lion Stance,2m ,Reflexive,None,Scene,Defend another reflexively.,346,Melee,4,1
        |Guard-Breaking Technique,3m ,Reflexive,None,Instant,"When Defending Other, grant extra Initiative to your charge with a Distract Gambit.",347,Melee,5,1
        |Solar Counterattack,3m ,Reflexive,"Counterattack, Decisive-only",Instant,Counterattack before damage is rolled against you.,347,Melee,4,1
        |Call the Blade,1m ,Reflexive,None,Instant,Call your weapon to hand with a successful roll.,347,Melee,1,1
        |Summoning the Loyal Steel,1m ,Simple,None,Indefinite,Store a weapon in Elsewhere.,347,Melee,3,1
        |Rising Sun Slash,1m ,Reflexive,Uniform,Instant,Add bonus successes if you roll a straight.,347,Melee,4,2
        |Agile Dragonfly Blade,-(3m) ,Permanent,None,Permanent,Treat your Initiative as being three higher for the purposes of OWTB.,347,Melee,5,2
        |Iron Whirlwind Attack,"5m, 1wp ",Simple,Decisive-only,Instant,Make a big magical flurry. Can chain off of PBT.,347,Melee,5,2
        |Fivefold Bulwark Stance,"5m, 1wp ",Simple,None,Scene,Ignore “certain” penalties to Defense.,348,Melee,5,2
        |Heavenly Guardian Defense,"1i/sux +4m, or 4m, or 4m, 1wp ",Reflexive,"Perilous, Decisive-only",Instant,Parry the unparryable.,349,Melee,5,2
        |Hail-Shattering Practice,1m/success ,Reflexive,None,Instant,Attacker loses 1 success for each 1 or 2 rolled.,349,Melee,3,2
        |Calm and Ready Focus,-,Permanent,None,Permanent,Gain 1i when parrying as part of a Defend Other.,349,Melee,4,2
        |Unassailable Guardian Posture,"1m, 1wp ",Reflexive,None,One turn,Attacks may not be applied to the character you’re defending.,349,Melee,5,2
        |Ready in Eight Directions Stance,5m ,Simple,"Counterattack, Decisive-only",Until next turn,Counterattack every attack for the next round.,349,Melee,5,2
        |Glorious Solar Saber,"5m, 1wp ",Simple,None,Scene,Summon a magical glowing sword.,350,Melee,3,2
        |Iron Raptor Technique,7m ,Simple,"Mute, Uniform",Instant,Make a Melee attack at Medium range.,350,Melee,4,2
        |Sandstorm-Wind Attack,"5m, 2i ",Simple,Decisive-only,Instant,"Make a Decisive attack at Short range, ignoring hardness and cover.",350,Melee,4,2
        |Edge of Morning Sunlight,2m ,Reflexive,Uniform,Instant,"Roll Decisive damage dice against Creatures of Darkness on a hit, even if that hit was Withering(!).",350,Melee,4,2
        |Foe-Cleaving Focus,-,Permanent,None,Permanent,Gain 1m for every 10 rolled when using RSS.,350,Melee,5,3
        |Hungry Tiger Technique,"3m, 2i ",Supplemental,"Dual, Perilous",Instant,Do extra damage to a Crashed opponent.,350,Melee,5,3
        |Invincible Fury of the Dawn,-,Permanent,None,Permanent,Gain extra attacks with IWA.,351,Melee,5,3
        |Perfect Strike Discipline,1wp ,Reflexive,None,Instant,Free full Excellency.,351,Melee,5,3
        |Flashing Edge of Dawn,"4m, 1wp ",Reflexive,"Counterattack, Withering-only",Instant,"Make a Withering counterattack, turning any stolen damage into Decisive dice.",351,Melee,5,3
        |Fervent Blow,"1m, 1wp ",Reflexive,"Clash, Decisive-only",Instant,Reflexively Clash with an attack.,352,Melee,5,3
        |Over-and-Under Method,"-(7m, 1wp)",Permanent,None,Permanent,Combine FEoD and FB.,352,Melee,5,3
        |Immortal Blade Triumphant,"10m, 1wp ",Simple,None,Scene,Extra damage on all attacks for the rest of the scene.,352,Melee,5,3
        |Corona of Radiance,"5m, 1wp ",Simple,None,Scene,Raise defense against creatures of darkness; burn them if they touch you.,352,Melee,5,3
        |Sharp Light of Judgment Stance,-,Permanent,None,Permanent,Gain extra damage against creatures of darkness; treat people you hate as creatures of darkness.,352,Melee,5,3
        |Blazing Solar Bolt,"5m, 2i, 1wp ",Simple,Decisive-only,Instant,Shoot a bolt of energy at an enemy; works better against creatures of darkness.,353,Melee,5,3
        |Heaven Sword Flash,"7m, 1wp ",Simple,"Decisive-only, Perilous",Instant,Decisively strike all opponents at close range.,353,Melee,5,4
        |Circle of Bright Reaving,"6m, 1a, 2i ",Reflexive,"Withering-only, Perilous",Instant,"If you kill anyone with HSF, make a withering strike against all opponents at close range. If you Crash anyone, you can pop HSF again.",353,Melee,5,5
        |Protection of Celestial Bliss,"- (4m, 1wp) ",Permanent,Decisive-only,Permanent,"When you use HGD, accumulate extra Initiative for later uses of HGD.",353,Melee,5,5
        |Spirit-Detecting Glance,3m ,Reflexive,None,Scene,See spirits.,354,Occult,1,1
        |Uncanny Perception Technique,-,Reflexive,None,Instant,A mental alarm goes off when there’s a spirit nearby.,354,Occult,2,1
        |Keen Unnatural Eye,-,Permanent,None,Permanent,Permanent bonus dice on attempts to investigate or follow spirits.,354,Occult,3,1
        |Spirit-Cutting Attack,1m ,Supplemental,Uniform,Instant,Strike a spirit.,354,Occult,2,1
        |Spirit-Draining Stance,5m ,Simple,None,Scene,Do extra damage to spirits for the scene.,354,Occult,3,1
        |Ghost-Eating Technique,3m ,Supplemental,Decisive-only,Instant,"Do aggravated damage to spirits, and steal motes from them. Permanently destroy spirits and, uh, imordials-Pray. (Shhh, don’t mention them by name!)",354,Occult,3,1
        |Phantom-Seizing Strike,"1m, 1wp ",Reflexive,Uniform,Instant,An immaterial enemy becomes material when you strike him.,355,Occult,4,1
        |Spirit-Slaying Stance,3m ,Simple,None,Scene,Scenelong bonus dice to attacks vs. spirits. Very cheap Infinite Occult Mastery.,355,Occult,5,1
        |Uncanny Shroud Defense,6m ,Reflexive,None,Instant,"An attack that would kill you doesn’t. You need to heal before using this again, though.",355,Occult,3,1
        |Spirit-Manifesting Word,1m ,Simple,None,Instant,Allow a spirit ally to materialize for 1m.,355,Occult,2,1
        |Ancient Tongue Understanding,6m ,Reflexive,None,Scene,Speak Old Realm and other spirit language. Bonus successes to shaping actions for the scene(!).,356,Occult,3,1
        |Supernal Control Method,-,Reflexive,None,Instant,Free full Occult Excellency.,356,Occult,5,1
        |All-Encompassing Sorcerer’s Sight,4m ,Reflexive,None,Instant,"Detect shaping actions, sorcery, Evocations, and spirit sanctums for one instant.",356,Occult,5,2
        |Carnal Spirit Rending,"5m, 1wp ",Reflexive,None,Instant,Temporarily absorb the powers of a spirit killed via GET.,356,Occult,5,2
        |Burning Exorcism Technique,"3m, 1wp ",Simple,None,Instant,Force a spirit or spiritual disease(?) from its host.,356,Occult,5,2
        |Breath-Drinker Method,4i ,Supplemental,Decisive-only,Instant,Steal motes equal to damage done to a spirit.,356,Occult,4,2
        |Spirit-Repelling Diagram,6m ,Simple,None,Scene,Spirits at short range must materialize or retreat.,357,Occult,3,2
        |Nine Specters Ban,"4m, 1wp ",Reflexive,None,Scene,Immaterial beings must pay Willpower to attack you.,357,Occult,4,2
        |Spirit-Caging Mandala,"10m, 1wp ",Simple,None,Varies,Trap and materialize a spirit for a few rounds.,357,Occult,4,2
        |Material Exegesis Prana,"3m, 1wp ",Simple,None,Instant,Materialize several spirit allies.,357,Occult,4,2
        |Dark-Minder’s Observances,-,Permanent,None,Instant,"Learn several thaumaturgical rituals. Er, what’s a thaumaturgical ritual?",357,Occult,4,2
        |Burning Eye of the Deliverer,-,Permanent,None,Permanent,Notice disguises and shapeshifting with AESS.,358,Occult,5,3
        |Soul Projection Method,6m ,Simple,None,Instant,Spiritually enter another’s body to do battle with possessing forces (or to inflict possessing forces on the target).,358,Occult,5,3
        |Wyld-Binding Prana,-,Permanent,None,Permanent,SCM works on unshaped fair folk and similar beings.,358,Occult,5,3
        |Spirit-Draining Mudra,-,Permanent,None,Permanent,Fuel your Charms and spells with motes stolen from a spirit trapped via SCM.,358,Occult,5,3
        |Demon-Compelling Noose,-,Permanent,None,Permanent,Gain social influence bonuses against a spirit trapped via SCM.,358,Occult,5,3
        |All Souls Benediction,"16m, 1wp ",Simple,None,Instant,Spirits to an enormous distance are forced to materialize and remain material.,358,Occult,5,3
        |Gloaming Eye Understanding,3m/Charm ,Reflexive,Stackable,Indefinite,Commit motes to learn spirit Charms.,359,Occult,5,3
        |Sorcerer’s Burning Chakra,-,Permanent,None,Permanent,"When at full anima, AESS automatically activates.",359,Occult,5,4
        |Immortal Soul Vigil,-(7m) ,Permanent,None,Permanent,"When using SPM, detect signs of past spiritual predation; come to subject’s aid if such predation recurs.",359,Occult,5,4
        |Spirit-Shredding Exorcism,"7m, 1wp ",Reflexive,None,Instant,Reduce Essence of a target trapped via SCM.,359,Occult,5,4
        |Spirit-Drawing Oculus,"1wp, 3a",Reflexive,None,Instant,Spend your anima to gather motes to spend on thinky-actions.,359,Occult,5,5
        |Ephemeral Induction Technique,"20m, 1wp ",Simple,Mute,Instant,Create a spirit.,360,Occult,5,5
        |Terrestrial Circle Sorcery,-,Permanent,None,Permanent,Terrestrial Circle Sorcery.,360,Occult,3,1
        |Celestial Circle Sorcery,-,Permanent,None,Permanent,Celestial Circle Sorcery.,361,Occult,4,3
        |Solar Circle Sorcery,-,Permanent,None,Permanent,Solar Circle Sorcery.,361,Occult,5,5
        |Masterful Performance Exercise,2m ,Supplemental,Mute,Instant,Bonus success + recurring 1s.,361,Perform.,3,1
        |Soul-Firing Performance,"1m, 1wp ",Simple,None,Instant,Inflict emotion on a group; all those affected reconsider a past decision in that emotional context.,361,Perform.,5,1
        |Stillness-Drawing Meditation,-,Permanent,None,Permanent,Recover motes when you influence a group.,361,Perform.,4,2
        |Trance of Fugue Vision,"5m, 1wp ",Reflexive,None,Scene,"Gain motes for rolling 10s or attempting performance, but only with scenelong Charms up.",362,Perform.,5,3
        |Penultimate Unity of Form,"2m, 1wp ",Reflexive,None,One Performance Action,Non-supplemental Performance Charms can be used with other performance types.,362,Perform.,5,3
        |Soul-Bracing Momentous Power,Varies ,Reflexive,None,Instant,Spend ~25% of your Willpower to make an influence roll more successful and harder to resist.,362,Perform.,5,3
        |Unmatched Showmanship Style,-,Permanent,Mute,Permanent,"1/scene, free full muted Excellency.",362,Perform.,5,4
        |Soul Voice,1wp ,Reflexive,None,One Performance action,"1/day, for the next performance, your Performance Charms cost no motes.",362,Perform.,5,5
        |Pivotal Encore Performance,"1wp, 1 lethal health level ",Reflexive,None,Instant,Reset USS and SV.,362,Perform.,5,5
        |Respect-Commanding Attitude,5m ,Supplemental,None,One Performance action,People must pay WP to leave or interrupt your performance.,362,Perform.,2,1
        |Phantom-Conjuring Performance,-(1m) ,Permanent,None,Permanent,Create illustrative illusions.,363,Perform.,4,2
        |Memory-Reweaving Discipline,"10m, 1wp ",Simple,Psyche,Instant,A group accepts a false memory. Announcing “Lelouch vi Britannia commands you!” optional.,363,Perform.,5,3
        |Memory-Reweaving Discipline x2,"5m, 1wp",Reflexive,Psyche,Instant,May only target a single character.,363,Perform.,5,4
        |Demon Wracking Shout,"10m, 1wp ",Simple,None,Instant,Vaporize creatures of darkness to long range with an extremely potent Decisive.,363,Perform.,5,3
        |Impassioned Orator Technique,1m ,Supplemental,None,Instant,"Double-9s, on oratory.",364,Perform.,3,1
        |Fury Inciting Speech,"5m, 1wp ",Simple,None,Instant,Incite a mob to action.,364,Perform.,4,2
        |Dogmatic Contagion Discipline,"1m, 1wp ",Supplemental,None,Instant,"On inspiring a crowd, grant bonus dice to those affected.",364,Perform.,5,2
        |Infectious Zealotry Approach,"1m, 1wp ",Reflexive,None,Instant,"Convince another character to attempt social influence now, forgoing all other purposes.",364,Perform.,5,3
        |Perfect Harmony Technique,1m ,Supplemental,None,Instant,"Double-9s, on music. Hang on, that seems familiar…",365,Perform.,3,1
        |Mood-Inducing Music,1m ,Simple,None,One song,"Grant or remove dice to attempts to create social influence, depending on whether it fits with the mood of your music.",365,Perform.,4,1
        |Battle Anthem (of the Solar Exalted),1m ,Simple,None,One song,Grant bonus dice to battle groups.,365,Perform.,5,2
        |Plectral Harbinger’s Approach,"3m, 1wp ",Reflexive,None,Instant,Grant bonus dice to a social influence.,366,Perform.,5,2
        |Heart-Compelling Method,"5m, 1wp ",Simple,Mute,Instant,"Appeal to an emotionally-charged Intimacy, creating Storyteller-defined action.",366,Perform.,4,2
        |Soul-Stirring Cantata,1m ,Simple,None,One song,Allies regenerate extra motes while listening to your music.,366,Perform.,5,2
        |Heroism-Encouraging Ballad,"6m, 1wp ",Simple,None,One song,"Either the Solar and an ally are immune to fear, or a larger group (or battle group) gain courage bonuses.",366,Perform.,5,3
        |Graceful Reed Dancing,1m,Supplemental,None,Instant,Gain double-9s while dancing.,366,Perform.,3,1
        |Battle-Dancer Method,1m,Reflexive,None,Instant,Add (Performance/2) to your Defense.,367,Perform.,4,1
        |Shining Expression Style,"2m, 1wp",Simple,None,Instant,Your dance inspires people to talk about their Ties.,367,Perform.,4,1
        |Winding Sinuous Motion,4m ,Simple,Mute,Instant,Reduce target’s Resolve or Guile by 2 vs. your next social influence.,367,Perform.,5,2
        |Monk-Seducing Demon Dance,"3m, 1wp ",Simple,Mute,Instant,Inspire characters to lust after or admire you.,367,Perform.,5,3
        |Master Thespian Style,1m ,Simple,None,Scene,Gain +1 Guile as long as you stay in character. Characters who fail to pierce your Guile read Intimacies for the character you’re playing.,367,Perform.,4,2
        |Voice-Hurling Method,2m ,Supplemental,Mute,Instant,Ventriloquism.,368,Perform.,4,1
        |Cunning Mimicry Technique,1m ,Supplemental,Mute,Instant,"Mimic a voice perfectly. For ten seconds, after an hour of warming up.",368,Perform.,3,1
        |Most Excellent Mockingbird,3m ,Reflexive,None,Scene,Mimic a voice perfectly for a scene.,368,Perform.,5,1
        |Splendid Magpie Approach,1m ,Supplemental,None,Instant,Mimic an animal.,368,Perform.,4,1
        |Thousand Courtesan Ways,5m ,Reflexive,None,Indefinite,+1 Appearance. Seduce without appealing to an Intimacy. Roll influence once; apply as different influence to all present.,368,Perform.,3,1
        |Celestial Bliss Trick,"3m, 1wp ",Simple,Mute,Instant,"Defining Tie of Lust. (Essence) auto-successes. “World-shaking climax.” … Stay classy, Exalted.",368,Perform.,4,1
        |Listener-Swaying Argument,3m ,Supplemental,None,Instant,"Gain bonuses to Instill or Persuade – bigger bonuses, if the target boosts his Resolve.",369,Presence,3,1
        |Harmonious Presence Meditation,5m or 7m ,Reflexive,None,Scene,Gain bonus dice to social rolls for a scene; social Charms are 1m cheaper.,369,Presence,2,1
        |Excellent Friend Approach,-,Permanent,None,Permanent,Anyone with Defining Ties to you will perform inconvenient tasks without a roll.,370,Presence,5,1
        |Tiger’s Dread Symmetry,-,Permanent,None,Permanent,"Permanent bonus to Presence and intimidate. With MRP, auto-intimidate anyone who tries to ambush you.",370,Presence,3,1
        |Impassioned Discourse Technique,3m ,Supplemental,None,Instant,Gain bonuses to persuasion in line with your Intimacies.,370,Presence,4,2
        |Empowering Shout,3m ,Simple,None,Instant,Grant an ally +1 to one Attribute and one Ability.,370,Presence,5,2
        |Majestic Radiant Presence,6m ,Simple,None,Scene,Enemies must pay 1wp the first time they speak or act against you.,370,Presence,4,2
        |Underling-Promoting Touch,"7m, 1wp ",Simple,None,Indefinite,Anyone with Intimacies for the Solar also has Intimacies for the target; the target can act with the Solar’s authority.,370,Presence,5,2
        |Threefold Magnetic Ardor,4m ,Supplemental,None,Instant,Turn your high-Appearance bonus dice into bonus successes.,371,Presence,4,2
        |Awakened Carnal Demiurge,"5m, 1wp ",Simple,None,Indefinite,Increase your Appearance by 1; lower the target’s Resolve if this is seduction.,371,Presence,5,2
        |Enemy-Castigating Solar Judgment,"3m, 1wp ",Supplemental,None,Instant,"A Decisive attack on creatures of darkness does agg, or a social influence attempt lowers their resolve. Treat people you hate as CoDs, for this one attack.",371,Presence,5,3
        |Fulminating Word,1wp ,Reflexive,None,Instant,"Increase the cost of a Decision Point (i.e., reject successful Persuade) by 1wp.",371,Presence,5,3
        |Authority-Radiating Stance,"5m, 1wp ",Reflexive,None,Scene,"Allies to Medium range are immune to fear penalties. Have we seen fear penalties, so far?",371,Presence,5,3
        |Terrifying Apparition of Glory,"7m, 1wp ",Reflexive,None,Scene,"Threaten everyone out to Long range. Also, inflict a fear penalty on battle groups – well, that answers that.",371,Presence,5,3
        |Blazing Glorious Icon,3m ,Reflexive,None,Instant,"Bonus dice to threaten, persuade, or instill.",372,Presence,5,3
        |Mind-Wiping Gaze,"1m, 1wp ",Reflexive,None,Instant,Target forgets one piece of social influence he was about to carry out.,372,Presence,5,3
        |Hypnotic Tongue Technique,"10m, 1wp ",Simple,"Psyche, Mute",Indefinite,Program the target with a series of short commands. Really wishing I hadn’t made a Code Geass joke already.,372,Presence,5,3
        |Worshipful Lackey Acquisition,-,Permanent,None,Permanent,"On succeeding at a major task, your enemies must grovel before you for the next few days.",372,Presence,5,3
        |Prophet-Uplifting Evocation,"4m, 1wp, 2xp ",Simple,None,Instant,Grant a follower motes and a Resolve bonus to act as your prophet.,373,Presence,5,3
        |Shedding Infinite Radiance,5m ,Simple,Stackable,Indefinite,Grant three bonus successes to a follower.,373,Presence,5,3
        |Rose-Lipped Seduction Style,"2m, 1wp ",Supplemental,None,Instant,Double-9s on seduction. Seduce even people who can’t be seduced.,373,Presence,5,3
        |Crowned King of Eternity,-,Reflexive,None,Instant,"1/scene, free Presence, Performance, or Socialize Excellency.",373,Presence,5,4
        |Favor-Conferring Prana,"5m, 1wp ",Simple,None,Indefinite,"As per ES, but stacking with that bonus and of Indefinite duration.",374,Presence,5,4
        |Countenance of Vast Wrath,"6m, 3a ",Reflexive,None,Scene,Burn your anima for extra Intimidation dice. Enemies who don’t attack you lose 1i/turn.,374,Presence,5,4
        |Durability of Oak Meditation,3m ,Reflexive,Dual,One tick,"4 Hardness, -2 damage to all attacks for one tick.",374,Resistan.,2,1
        |Spirit Strengthens the Skin,1m/damage die removed ,Reflexive,Withering-only,Instant,Post-hit soak booster. Upgrades to remove damage successes.,374,Resistan.,2,1
        |Iron Skin Concentration,2m or 6m ,Reflexive,Dual,Instant or Indefinite,"Soak unsoakable withering damage; against a Decisive attack, create extra health levels.",374,Resistan.,3,1
        |Ox-Body Technique,-,Permanent,Stackable,Permanent,Extra health levels.,375,Resistan.,1,1
        |Body-Mending Meditation,10m ,Simple,Mute,Instant,Heal fast. (Recommended with lots of OBT.),375,Resistan.,2,1
        |Front-Line Warrior’s Stamina,4m ,Simple,Perilous,Instant,Gain [~.25*your health levels]i.,376,Resistan.,3,1
        |Whirlwind Armor-Donning Prana,2m ,Simple,None,Instant,"Put on armor in a handful of turns. Er, how long does it take normally?",376,Resistan.,1,1
        |Armored Scout’s Invigoration,4m or 6m ,Reflexive,None,Indefinite,"Once your armor is on, its Mobility penalty is 0.",376,Resistan.,3,1
        |Poison-Resisting Meditation,3m ,Reflexive,None,Scene,Scenelong bonus dice vs. poison.,376,Resistan.,3,1
        |Essence-Gathering Temper,1i ,Reflexive,"Perilous, Withering-only",Instant,"When struck by a Withering attack, pay 1i to recover (damage dice/2)m.",376,Resistan.,3,1
        |Diamond-Body Prana,5m ,Simple,Dual,Scene,"Become immune to minor scenery damage, from brambles(?) to bonfires. Gain soak or hardness. Can’t be used in armor.",376,Resistan.,4,2
        |Iron Kettle Body,6m ,Reflexive,Withering-only,One turn,Halve (or more) post-soak damage of a Withering attack.,377,Resistan.,4,2
        |Adamant Skin Technique,8m ,Reflexive,Decisive-only,Instant,Apply soak to a Decisive attack(!). Perfect soak vs. uncountable damage.,377,Resistan.,5,2
        |Tiger Warrior’s Endurance,-,Permanent,None,Permanent,"1/fight, heal when you recover from Crash.",377,Resistan.,5,2
        |Hauberk-Summoning Gesture,3m ,Reflexive,None,Instant,Store your armor in Elsewhere.,377,Resistan.,3,2
        |Illness-Resisting Meditation,4m ,Reflexive,None,One day,Bonus successes vs. illness.,377,Resistan.,4,2
        |Willpower-Enhancing Spirit,2i ,Reflexive,"Perilous, Decisive-only",Instant,"1/fight, when struck by a Decisive, pay 2i to gain 1wp.",377,Resistan.,4,2
        |Battle Fury Focus,5m ,Simple,None,Scene,Go into a berserk rage. Gain +1 die to combat pools; reduce wound penalties by 1.,378,Resistan.,5,2
        |Wound-Knitting Exercise,1m/-0 health level ,Simple,None,Until fully healed,"Heal your -0 health levels gradually, but in combat time.",378,Resistan.,5,3
        |Unbreakable Warrior’s Mastery,3m ,Reflexive,None,Instant,Reduce the penalty and duration of a crippling attack. (What’s a crippling attack?),378,Resistan.,5,3
        |Ruin-Abasing Shrug,4m ,Reflexive,Decisive-only,Instant,"1/scene, force attacker to reroll successful damage dice.",378,Resistan.,5,3
        |Glorious Solar Plate,"10m, 1wp ",Simple,None,Indefinite,Summon magical glowing sun-armor. (Still not a technique!),378,Resistan.,4,3
        |Immunity to Everything Technique,"6m, 1wp ",Reflexive,None,One day,"Gain immunity to any poison or disease you’ve encountered before, and resistance to the rest.",379,Resistan.,5,3
        |Fury-Fed Ardor,"3m, 1wp ",Reflexive,Decisive-only,Instant,"When struck by a Decisive attack, gain (half the non-successful damage dice)i.",379,Resistan.,5,3
        |Bloodthirsty Sword-Dancer Spirit,"10m, 1wp ",Simple,None,Scene,"While under BFF, go into a more-berserk (berserker?) rage. Gain +3 dice to combat pools; ignore wound penalties; recover 1m/turn.",379,Resistan.,5,3
        |Aegis of Invincible Might,-,Permanent,"Dual, Perilous",Permanent,"Gain 20 Hardness, bonus soak, and post-soak damage negation, but only as long as you make big attacks every turn.",380,Resistan.,5,5
        |Master Horseman’s Techniques,-,Permanent,None,Permanent,"Gain a number of minor mount-enhancing Abilities – prevent fatigue, call it to you, etc.",380,Ride,1,1
        |Flashing Thunderbolt Steed,4m ,Reflexive,None,One hour,"Mount resists fatigue, gains a movement success, and can use Graceful Crane Stance/Monkey Leap Technique.",380,Ride,2,1
        |Elusive Mount Technique,4m ,Reflexive,None,Instant,Reflexively Disengage while you are mounted.,380,Ride,3,1
        |Wind-Racing Essence Infusion,"2m, 1wp or 4m, 1wp ",Reflexive,None,Instant,"Your mount moves at greater speeds while outside combat. (How fast does it normally move, outside combat?)",380,Ride,3,1
        |Single Spirit Method,1m ,Reflexive,None,Instant,Mount can rise from prone for 1m.,381,Ride,3,1""" ,
    """
        |Seasoned Beast-Rider’s Approach,"1m, 1wp ",Supplemental,None,Instant,"Your mount gains its own move and attack actions, as well as its own Initiative track.",381,Ride,2,1
        |Worthy Mount Technique,-(1i) ,Reflexive,None,Permanent,Your mount can reflexively Defend Other on you.,381,Ride,3,1
        |Mount Preservation Method,1hl/three successes ,Reflexive,None,Instant,"Spend your health levels on behalf of your mount, at a reduced rate.",381,Ride,3,1
        |Harmonious Tacking Technique,2m to 6m ,Simple,None,Instant,Tack and bard a mount in 1-4 rounds.,381,Ride,3,2
        |Untouchable Horseman’s Attitude,"3m, 2i, 1wp ",Reflexive,Perilous,Instant,Automatically succeed at a Disengage as long as only one enemy is at close range.,382,Ride,4,2
        |Immortal Charger’s Gallop,1m ,Supplemental,None,Instant,Convert mount’s speed bonus to successes.,382,Ride,4,2
        |Supernal Lash Discipline,5m ,Reflexive,Perilous,Scene,Double mount’s speed for the scene.,382,Ride,5,2
        |Speed-Fury Focus,-(3m) ,Permanent,None,Permanent,ICG can be used to enhance a Join Battle roll.,382,Ride,5,2
        |Inexhaustible Destrier’s Gait,2m ,Reflexive,None,One turn,Your mount ignores wound penalties.,382,Ride,5,2
        |Coursing Firebolt Flash,"3m or 4m, 1a ",Supplemental,None,Instant,Recurring 1s on a mounted movement roll; leave a trail of bonfire flames in your wake.,382,Ride,5,2
        |Saddle-Staying Courses,"4m, 3i, 1wp ",Reflexive,None,Instant,Recover instantly from an unhorse Gambit.,382,Ride,4,2
        |Horse-Stealing Leap,"3m, 1wp ",Supplemental,None,Instant,Unhorse someone and steal his mount.,383,Ride,5,2
        |Immortal Rider’s Advantage,-,Reflexive,None,Instant,"Transfer half your Initiative to your mount, or vice-versa.",383,Ride,3,2
        |Horse-Healing Technique,"4m, 1hl or 4m, 1lhl ",Simple,None,Instant,"Over a scene of treatment, absorb and reduce your mount’s wounds.",383,Ride,5,2
        |Rousing Backlash Assault,5m ,Reflexive,"Counterattack, Decisive-only",Instant,"While using WMT and SBR, your mount may Decisively counterattack for attacks on you.",383,Ride,4,2
        |Woe and Storm Evasion,4m ,Reflexive,None,Instant,"Your mount (barely) avoids Crash or death, as long as it had at least 2i or 2 HLs to begin with.",383,Ride,4,2
        |Resilience of the Chosen Mount,2m ,Reflexive,None,Instant,An attack on your mount loses 1 damage per 1 or 2 rolled.,383,Ride,5,2
        |Phantom Steed,"10m, 1wp ",Reflexive,None,One day,"Summon an Essence-horse. But it’s an expression of skill, and no one would try to label this a… yeah, you get the joke.",383,Ride,5,3
        |Hero Rides Away,-,Permanent,None,Permanent,"Ride off into the sunset. Recover motes and Willpower, and remove a point of Limit.",384,Ride,5,3
        |Phantom Rider’s Approach,7m ,Supplemental,None,Instant,"If someone approaches you after a Disengage, retreat two range bands.",384,Ride,5,3
        |Fierce Charger’s Pulse,-,Permanent,None,Permanent,"For each 10 the enemy rolls, gain 1m for use on Ride Charms next turn.",384,Ride,5,3
        |Grizzled Cataphract’s Way,1m ,Reflexive,None,Instant,Substitute Ride for Awareness in Join Battle.,384,Ride,5,3
        |Rapid Cavalry Approach,7m or 12m ,Simple,Perilous,Scene,"Move three range bands a turn at extreme range from all enemies, or two bands/turn at closer ranges.",385,Ride,5,3
        |Sometimes Horses Fly Approach,1m ,Reflexive,None,One turn,Your mount runs on water or air for one turn.,385,Ride,5,3
        |Soaring Pegasus Style,"2m, 1wp ",Reflexive,None,Instant,Rush an enemy in the air.,385,Ride,5,3
        |Whirlwind Horse-Armoring Prana,"1m to 3m or 5m, 1wp ",Reflexive,None,Instant,"Summon your mount’s tack, barding, and weapons from Elsewhere.",385,Ride,5,3
        |Bard-Lightening Prana,4m ,Reflexive,None,One day,Mount armor has no Mobility penalty.,385,Ride,5,3
        |Untouchable Solar Steed,-,Permanent,None,Permanent,Activate “attack-evasive Dodge Charms” on behalf of your mount.,385,Ride,5,3
        |Wrathful Mount Invigoration,-,Permanent,None,Permanent,"Your mount gains 1i/turn, plus bonuses when you outrace an enemy.",386,Ride,4,3
        |Seven Cyclones Rearing,"5m, 1wp ",Reflexive,"Clash, Decisive-only",Instant,"While using WMT and SBR, your mount may (reflexively?) Decisively Clash with attacks aimed at you.",386,Ride,5,3
        |Iron Simhata Style,5m ,Simple,None,Scene,Your mount gains bonus soak for the scene.,386,Ride,5,3
        |Salty Dog Method,-,Permanent,None,Permanent,Permanent benefits: recurring 6s on Sail; add Sail/2 to Resolve vs. supernatural fear; recover instantly from a fall; find your way to anywhere you’ve been before.,386,Sail,3,1
        |Shipwreck-Surviving Stamina,-,Permanent,None,Permanent,"Permanently +2 Stamina for resisting deprivation (food, oxygen, etc.).",386,Sail,3,1
        |Fathoms-Fed Spirit,-,Permanent,None,Permanent,"Permanent bonus: if you wake up on a ship, you can ignore the WP cost to resist one social influence that day.",386,Sail,5,1
        |Safe Bearing Technique,4m ,Simple,None,Until the hazard has passed,"+2 dice to avoid hazards, or +2 successes if you’ve avoided these specific hazards before.",386,Sail,3,1
        |Ship-Claiming Stance,"5m, 1wp ",Simple,None,Instant,Anyone on your ship without permission takes -1 die. You can pull 5m from the ship.,387,Sail,3,1
        |Ship-Sleeking Technique,4m ,Simple,None,Indefinite,Ship’s Speed +1.,387,Sail,3,1
        |Orichalcum Letters of Marque,-,Permanent,None,Permanent,"Creatures of darkness in your crew don’t count as such for the purpose of social magic, your anima, or your area-of-effect attacks.",387,Sail,5,1
        |Immortal Mariner’s Advantage,1 or 2m ,Reflexive,None,Instant,"Double-9s or recurring 1s on a Sail roll, after the roll is made (!).",387,Sail,5,1
        |Legendary Captain’s Signature,3m ,Supplemental,Pilot,Instant,Double ship’s Maneuverability and add 1 to its Speed for one maneuver.,388,Sail,5,1
        |Sea Ambush Technique,2m ,Supplemental,Pilot,Instant,+Speed to a Conceal maneuver.,388,Sail,5,1
        |Deck-Sweeping Fusillade,Varies ,Supplemental,Pilot,Instant,+Essence dice and +Essence non-Charm successes to a Broadside maneuver.,388,Sail,5,1
        |Ship-Breaker Method,3m ,Supplemental,Pilot,Instant,+1 level of damage when ramming the enemy; cancel the enemy’s Broadside maneuver.,389,Sail,5,1
        |Superior Positioning Technique,2m ,Supplemental,Pilot,Instant,"In a Positioning maneuver, treat enemy 1s as your 10s. Cancel the enemy’s Ram maneuver.",389,Sail,5,1
        |Ship-Imperiled Vigor,-,Permanent,None,Permanent,"Permanently gain X dice to your ship-defending actions, where -X is your ship’s hull penalty.",389,Sail,4,2
        |Weather-Anticipating Intuition,5m ,Simple,None,Instant,Predict the weather for the next day or so.,389,Sail,4,2
        |Tide-Cutting Essence Infusion,"5m, 1wp ",Reflexive,None,One day,Ship’s Speed +1 for one day. Incompatible with WRD.,389,Sail,4,2
        |Wave-Riding Discipline,"5m, 1wp ",Reflexive,None,One day,"Double ship’s Speed bonus for sails, but reduce any rower bonus to +1. No bonus for being dragged by sea monsters. Incompatible with TCE.",389,Sail,4,2
        |Hull-Preserving Technique,"5m, 1wp ",Reflexive,None,One turn,Negate all damage to the ship for one turn.,389,Sail,5,2
        |Hull-Taming Transfusion,"2m, 3hls/1hul ",Reflexive,None,Instant,Spend 3HL to heal one level of damage to your ship.,390,Sail,5,2
        |Ship-Leavening Meditation,5m ,Simple,None,Scene,Negate your ship’s hull penalty.,390,Sail,5,2
        |Indomitable Voyager’s Perseverance,1wp ,Reflexive,None,Instant,Reroll all nonsuccesses on a Sail action.,390,Sail,5,2
        |Ocean-Conquering Avatar,1m ,Reflexive,None,One turn,"1/scene, free full Sail Excellency.",390,Sail,5,2
        |Wind-Defying Course Technique,3m ,Reflexive,None,One hour,"Reduce wind-based penalties (are those a thing, prior to now?) by 3.",390,Sail,5,2
        |Current-Cutting Technique,4m ,Reflexive,None,One hour,Reduce curret-based penalties to speed by 2 and from sea monsters.,390,Sail,5,2
        |Implacable Sea Wolf Spirit,4m ,Reflexive,Pilot,Scene,-2 Momentum to the cost of all maneuvers for the rest of the scene.,390,Sail,5,2
        |Deadly Ichneumon Assault,"2m, 1wp ",Reflexive,Pilot,Instant,"On a successful ram, also shock and board.",391,Sail,5,2
        |Rail-Storming Fervor,2m ,Supplemental,Pilot,Instant,+3 non-Charm dice to Join Battle on a boat.,391,Sail,5,2
        |Sea Serpent Flash,5m ,Supplemental,Pilot,Instant,Double Momentum gained via a Positioning maneuver.,391,Sail,5,2
        |Tide-Carried Omens,7m ,Simple,None,Indefinite,"Sense danger (Essence) minutes early, apparently whether on a boat or not. Bonus successes to Awareness on a boat.",391,Sail,5,3
        |Chaos-Cutting Galley,"10m, 1wp ",Reflexive,None,One day,Render the ship and crew immune to the Wyld.,391,Sail,5,3
        |Blood and Salt Bondage,"10m, 1wp ",Reflexive,None,Indefinite,Gain extra health levels equal to the ship’s current hull.,391,Sail,5,3
        |Ship-Sustaining Spirit,4m ,Reflexive,Stackable,Indefinite,Commit motes to keep your ship from falling apart at 0 health.,392,Sail,5,3
        |Burning Anima Sails,"6m, 1wp, 3a",Reflexive,None,Scene,Expend your anima to make SWEET BURNING SUN-SAILS that terrify creatures of darkness.,392,Sail,5,3
        |Storm-Weathering Essence Infusion,"6m, 1wp ",Simple,Pilot,One hour,+(Essence) auto-successes to fighting a storm for the next hour.,392,Sail,5,3
        |Invincible Admiral Method,"10m, 1wp ",Simple,None,Scene,"Give bonus dice to your fleet, when maneuvering.",392,Sail,5,3
        |Sea Devil Training Technique,"10m, 1wp ",Simple,None,Indefinite,Permanently enhance your sailors’ stats.,392,Sail,5,3
        |Ship-Rolling Juggernaut Method,3m ,Reflexive,Pilot,Instant,"On incapacitating another ship with a maneuver, you keep all your existing Momentum (and gain more!) instead of losing it.",393,Sail,5,3
        |Ship-Razing Renewal,-,Permanent,Pilot,Permanent,"On incapacitating another ship with a maneuver, gain motes and Willpower.",393,Sail,5,3
        |Black Fathoms Blessed,"10m, 1wp ",Simple,None,Scene,"Add your ship’s Speed to your personal movement, and its Maneuverability to your Defense. Speak an ancient ocean language. Become immune to drowning.",393,Sail,5,3
        |Mastery of Small Manners,5m ,Reflexive,None,Scene,You are incapable of faux pas. People with Ties to the local culture have Minor Ties to you.,393,Socialize,3,1
        |Motive-Discerning Technique,3m ,Supplemental,Mute,Instant,Read intentions with double-9s.,393,Socialize,3,1
        |Motive-Discerning Technique x2,3m ,Supplemental,Mute,Instant,"Player can guess at a related Intimacy, if correct character knows it.",393,Socialize,4,1
        |Motive-Discerning Technique x3,3m ,Supplemental,Mute,Instant,"If player is incorrect on guess, they may guess again for a second Intimacy.",393,Socialize,5,1
        |Quicksilver Falcon’s Eye,1m ,Reflexive,None,One turn,Sense any application of Resolve or Guile in the next turn.,394,Socialize,3,1
        |Umbral Eyes Focus,2m ,Supplemental,Mute,Instant,Know the intensity of all Intimacies you’ve Instilled yourself in the target.,394,Socialize,5,1
        |Humble Servant Approach,1m ,Reflexive,None,Instant,"After Reading Intentions of the target while he was talking to someone else, he takes -2 Guile when you Read Intentions while he’s talking to you.",394,Socialize,4,1
        |Shadow Over Day,1m or 2m ,Reflexive,Mute,Instant,"1m for +1 Guile, or 2m for +2 Guile.",394,Socialize,3,1
        |Night Passes Over,2m ,Reflexive,None,Instant,"Ignore all penalties to Guile, except wound penalties and the penalty for thinking you’re alone.",394,Socialize,4,1
        |Intent-Tracing Stare,1m ,Reflexive,Mute,Instant,Detect when an NPC Reads Intentions on someone else nearby.,394,Socialize,5,1
        |Culture Hero Approach,3m ,Simple,None,Instant,"On observing a ritual, discern its significance. Bonus successes if you have to perform the rite.",394,Socialize,4,2
        |Unimpeachable Discourse Technique,3m ,Supplemental,None,Instant,Recurring 1s any time MoSM would apply. (Always?),395,Socialize,5,2
        |Indecent Proposal Method,4m ,Supplemental,Mute,Instant,"Intimacies towards you will not drop as a result of any Persuade attempt you make, unless characters pay 1wp.",395,Socialize,5,2
        |Dauntless Assayer Method,5m ,Reflexive,Mute,Instant,Retry a Read Intentions action on the same target.,395,Socialize,5,2
        |Preeminent Gala Knife,-,Permanent,None,Permanent,"Gain 2m when you succeed at a Socialize action, to a max of the Socialize motes you’ve spent this scene.",395,Socialize,5,2
        |Wise-Eyed Courtier Method,6m ,Simple,None,Instant,Read Intentions on everyone in the scene.,395,Socialize,5,2
        |Discretionary Gesture,3m ,Reflexive,None,Instant,Boost an ally’s Guile. By glaring daggers at him. Awesome.,396,Socialize,5,2
        |Deep-Eyed Soul Gazing,3m ,Reflexive,None,Instant,Bonus dice when Reading Intentions on someone who failed to do the same to you.,396,Socialize,5,2
        |Easily-Discarded Presence Method,"3m, 1wp ",Reflexive,Mute,Instant,"After defending against Read Intentions, pretend you failed, the target will disregard you.",396,Socialize,5,2
        |Guarded Thoughts Meditation,4m ,Simple,None,Indefinite,Infinite Socialize Mastery; only applies to boosting Guile.,396,Socialize,5,2
        |Penumbra Self Meditation,3m/Intimacy ,Simple,Stackable,Indefinite,Perfectly conceal an Intimacy.,397,Socialize,5,2
        |Inverted Ego Mask,"2m, 1wp ",Reflexive,None,Instant,"If an Intimacy would be detected, you can change it to something else to fool the person looking. If you Limit Break, though, your pretend Intimacy might become real!",397,Socialize,5,2
        |Cunning Insight Technique,3m ,Reflexive,None,Instant,"Witnessing a social action, you may guess at the Intimacy behind it. If correct, you discern an Intimacy without having to roll Read Intentions.",397,Socialize,5,3
        |Doubt-Sowing Contention Method,"6m, 1wp ",Simple,None,Instant,Preempt a target about to perform social influence; that influence is treated as if it had already been tried and failed.,397,Socialize,5,3
        |Effective Counterargument,"6m, 1wp ",Reflexive,Counterattack,Instant,"1/scene, intervene after social influence to boost the target’s Resolve with a counterargument.",397,Socialize,5,3
        |Wise Counsel (Flashing Soul Reform),1wp ,Simple,None,Instant,Advise another to grant bonus successes for use on future social actions. Target gains an Intimacy for you.,398,Socialize,5,3
        |Endless Obsession Feint,-,Permanent,None,Permanent,"Anyone you defeat socially becomes obsessed with you, taking die penalties and coming to love or hate you.",398,Socialize,5,3
        |Aspersions Cast Aside,5m ,Reflexive,Mute,Instant,"Got a pencil handy? Okay, take your 1s and 2s, and give them to someone else nearby. He rolls; find his lowest success number, and for each 2 you have, cancel one of those. Find his next success number (i.e., 8 if the first one was 7), and for each 1 you have, cancel one of those. You gain successes equal to those canceled for him; if he doesn’t roll well enough, he looks ridiculous.",398,Socialize,5,3
        |Asp Bites Its Tail,4m ,Reflexive,"Counterattack, Mute",Instant,"If a target tries to turn a third party against you, the aspersions fall back on the target instead.",398,Socialize,5,3
        |Fete-Watcher Stance,-,Permanent,None,Permanent,Bonus dice to Join Battle and Awareness if you sense hostility in the room.,398,Socialize,5,3
        |Seen and Seeing Method,"2m, 1wp ",Reflexive,None,Instant,"When you block another’s Read Intentions, you may reply with one of your own.",399,Socialize,5,3
        |Seen and Seeing Method x2,"2m, 1wp ",Reflexive,None,Instant,Can notice a Read Intentions from a hidden source.,399,Socialize,5,3
        |Seen and Seeing Method x3,"2m, 1wp ",Reflexive,None,Instant,Can response with a Read Intentions even if theirs piereces your Guile.,399,Socialize,5,4
        |Face-Charming Prana,6m ,Simple,None,Instant,Compel another to Read Intentions on you.,399,Socialize,5,3
        |Selfsame Master Procurer,"4m, 1wp ",Reflexive,Mute,Instant,"If a target fails to Read Intentions on you, he believes he can use you to accomplish some aim.",399,Socialize,5,3
        |Soul-Void Kata,"4m, 1wp ",Reflexive,None,Instant,Hypnotize a character who fails to read your Guile.,399,Socialize,5,3
        |Knowing the Soul’s Price,"10m, 1wp ",Simple,None,Instant,Know what it would take to persuade someone to take a particular action.,399,Socialize,5,3
        |Understanding the Court,"20m, 1wp ",Simple,None,Instant,"Sense the Ties, mannerisms, and behavior of everyone involved in court, including those currently absent.",400,Socialize,5,3
        |Unbound Social Mastery,-,Permanent,None,Permanent,"1/scene, free full Socialize Excellency. This Charm references “venting a point of Limit” by achieving a legendary social goal – is that a thing mentioned anywhere else?",400,Socialize,5,3
        |Heart-Eclipsing Shroud,"-(10m, 1wp) ",Permanent,Mute,Permanent,"Over the course of a few hours, shift into a different persona with its own Intimacies.",400,Socialize,5,3
        |Hundred-Faced Stranger,-,Permanent,None,Permanent,Your personas have some of your Ability dots + ½ your total XP to spend on more.,401,Socialize,5,3
        |Legend Mask Methodology,-,Permanent,None,Permanent,Your personas may learn Charms.,401,Socialize,5,3
        |Friend of a Friend Approach,-,Permanent,None,Permanent,Someone who loves someone who loves you also has a Minor Tie for you.,402,Socialize,5,3
        |Venomous Rumors Technique,"10m, 1wp ",Simple,None,Instant,Curse someone to be hated by a social group.,402,Socialize,5,4
        |Even-Touched Prophet,-,Permanent,None,Permanent,"1/scene, double-8s on a Socialize roll.",402,Socialize,5,4
        |Elusive Dream Defense,"1m, 1wp ",Reflexive,None,Scene,"1/story, add your Guile to your Resolve or vice-versa. Ignore effect of Intimacies on Resolve.",402,Socialize,5,4
        |Draw the Curtain,-,Permanent,None,Permanent,"One persona has 75% of your total XP, plus 2/3 of any future XP. (What?)",402,Socialize,5,4
        |At Your Service,"10m, 1wp ",Reflexive,Mute,Instant,Craft a custom persona to meet someone’s needs.,402,Socialize,5,5
        |Fugue-Empowered Other,1 Limit ,Reflexive,None,Instant,"Access a persona’s Abilities and Charms for one tick. On Limit Break, shuffle your Intimacies with the persona’s Intimacies.",403,Socialize,5,5
        |Soul Reprisal,"16m, 1wp, 20xp ",Reflexive,None,Instant,"On death, you regenerate into a persona as per Doctor Who. Like, blatantly as per Doctor Who.",403,Socialize,5,5
        |Perfect Shadow Stillness,"1m, 1wp ",Reflexive,None,Instant,Reroll all dice other than 10s on a Stealth action.,403,Stealth,2,1
        |Invisible Statue Spirit,5m ,Reflexive,Mute,Indefinite,Perfect immunity to sight-based detection as long as you don’t move.,403,Stealth,3,1
        |Easily-Overlooked Presence Method,3m ,Simple,None,Scene,Blend perfectly in the crowd. Checkpoints or magic actively opposing your cause may still detect you.,403,Stealth,3,1
        |Blinding Battle Feint,3m ,Supplemental,None,Instant,"Join Battle using Stealth; if you act first, you’re also hidden.",404,Stealth,3,1
        |Stalking Wolf Attitude,5m ,Simple,Mute,Indefinite,Ignore the -3 penalty to moving in stealth. Gain Initiative while you remain hidden.,404,Stealth,4,1
        |Guardian Fog Approach,3m ,Simple,Mute,Instant,Hide someone else.,405,Stealth,3,1
        |Blurred Form Style,"7m, 1wp ",Reflexive,Mute,Indefinite,"Infinite Stealth Mastery. You can hide in plain sight, but moving range bands automatically reveals you.",405,Stealth,4,2
        |Mental Invisibility Technique,"5m, 1wp ",Simple,Mute,Scene,"Roll Stealth against target’s Resolve; if you succeed, you’re imperceptible to him until you Join Battle or try to steal something the target cares about.",405,Stealth,4,2
        |Shadow Victor’s Repose,"3m, 1wp ",Simple,Mute,Instant,Reroll Join Battle mid-fight and add the Initiative to your pool.,405,Stealth,4,2
        |Flash-Eyed Killer’s Insight,2m ,Reflexive,None,Instant,Reset SVR after incapacitating an enemy.,405,Stealth,5,2
        |Hidden Snake Recoil,1wp or 2i ,Reflexive,None,Instant,Enter stealth reflexively after incapacitating an enemy.,405,Stealth,5,2
        |Dark Sentinel’s Way,1m ,Reflexive,Mute,Instant,Defend Other from concealment.,406,Stealth,4,2
        |Smoke and Shadow Cover,3m ,Reflexive,Mute,Instant,Concealment counts as cover.,406,Stealth,5,2
        |Sun Swallowing Practice,2m/anima level ,Reflexive,"Mute, Stackable",Indefinite,Commit motes to temporarily hide anima levels.,406,Stealth,5,3
        |Vanishing From Mind’s Eye Method,"10m, 1wp ",Reflexive,Mute,Indefinite,Hide so well that everyone forgets about you.,407,Stealth,5,3
        |Sound and Scent Banishing Attitude,6m ,Reflexive,"Mute, Stackable",One hour,Render yourself proof against detection from one sense (except sight).,407,Stealth,5,3
        |Ten Whispers Silence Meditation,3m ,Reflexive,None,Scene,Attempts to detect you by hearing alone take -1 success for each 1 or 2 rolled.,407,Stealth,5,3
        |Mind Shroud Meditation,-,Permanent,None,Permanent,"On using SVR, everyone forgets you were there for (Essence) rounds.",407,Stealth,5,3
        |Shadow Replacement Technique,"8m, 1wp ",Reflexive,"Perilous, Mute",Indefinite,"Hide in a target’s shadow, controlling his body.",407,Stealth,5,3
        |Shadow-Crossing Leap Technique,"5m, 1wp ",Reflexive,Mute,Instant,Move one range band in stealth without penalty; incompatible with BFS.,408,Stealth,5,3
        |Fivefold Shadow Burial,-,Permanent,None,Permanent,Enemies permanently take -2 successes for each 1 rolled on Awareness rolls.,408,Stealth,5,4
        |False Image Feint,"7m, 1wp ",Reflexive,"Perilous, Mute",Instant,"Still got that pencil? So the attacker rolls and hits you Decisively, and then he rolls damage. Between the two, he rolls six 1s and 2s. You roll Stealth against his Awareness; if you succeed, he misses, and if you roll a 10, you enter stealth.",408,Stealth,5,4
        |Flashing Nocturne Prana,"10m, 1wp ",Reflexive,"Perilous, Mute",Indefinite,Teleport back to a chosen hiding place.,409,Stealth,5,4
        |Food-Gathering Exercise,3m ,Simple,None,One hour,Gather enough food for one or more people in any environment.,409,Survival,1,1
        |Hardship-Surviving Mendicant Spirit,5m ,Reflexive,None,One day,Remove penalties and minor troubles for rough environment. Does not resist environmental damage.,409,Survival,3,1
        |Friendship with Animals Approach,3m ,Simple,None,Scene,"Wild animals are friendly, or at least won’t attack.",409,Survival,2,1
        |Trackless Region Navigation,"5m, 1wp ",Reflexive,None,Indefinite,Lead a small group safely through the wilderness at 20 miles/day. (How fast is normal?),409,Survival,4,2
        |Unshakeable Bloodhound Technique,4m ,Supplemental,None,Instant,"When tracking another, you have recurring 5s and 6s and his 1s count as 10s for you.",410,Survival,5,2
        |Spirit-Tied Pet,"10m, 1wp, 1xp ",Simple,None,Instant,Your familiar is unshakably loyal and produces motes and willpower. You can see through its senses.,410,Survival,3,2
        |Beast-Mastering Behavior,"10m, 1wp ",Simple,None,One week,"Train a new familiar, or train an existing familiar to use its latent, magical, or blatantly supernatural powers.",410,Survival,4,2
        |Deadly Onslaught Coordination,3m ,Reflexive,None,Instant,Call your familiar to attack alongside you.,411,Survival,5,2
        |Bestial Traits Technique,"10m, 1wp, 2xp ",Supplemental,None,Instant,Recurring 1s on a training roll. Grant a familiar bonus stats with an XP investment.,411,Survival,4,2
        |Hide-Hardening Practice,-,Permanent,Stackable,Permanent,Permanent soak/hardness boost for your familiar.,411,Survival,5,2
        |Life of the Aurochs,-,Permanent,Stackable,Permanent,Extra health levels for your familiar.,411,Survival,5,2
        |Familiar-Honing Instruction,4m ,Simple,Mute,Instant,"Command your familiar, granting it bonus dice on its attacks.",411,Survival,3,2
        |Spirit-Hunting Hound,1m ,Reflexive,None,One turn,Your familiar can see dematerialized spirits.,412,Survival,5,2
        |Ambush Predator Style,3m ,Reflexive,Mute,Instant,"Your familiar gets to use your Join Battle roll, with bonus successes. Bonus dice granted to this action by FHI gain recurring successes.",412,Survival,3,2
        |Element-Resisting Prana,-,Permanent,None,Permanent,Ignore drowning and toxic gas; reduce all environmental damage by your Resistance.,412,Survival,5,3
        |Traceless Passage,"3m, 1wp ",Supplemental,None,Instant,Bonuses to your attempts to leave no tracks with a small group. Mundane tracking automatically fails.,412,Survival,5,3
        |Eye-Deceiving Camouflage,6m ,Simple,None,Indefinite,Perfectly conceal yourself or your stash against mundane senses.,412,Survival,5,3
        |Red-Toothed Execution Order,5m ,Reflexive,Decisive-only,Instant,"1/scene, your Familiar’s Decisive attacks roll over extra successes into damage.",413,Survival,5,3
        |Ghost Panther Slinking,-,Permanent,None,Permanent,Use your Stealth Charms while possessing your familiar.,413,Survival,5,3
        |Saga Beast Virtue,"5m, 1wp ",Reflexive,None,Indefinite,"Your familiar grows, gains stat boosts, and develops beneficial mutations. (Wings!)",413,Survival,5,3
        |Phantom-Rending Fangs,3m ,Reflexive,None,One turn,"Your familiar grapples spirits, making them vulnerable to you. Cheaper in conjunction with FHI.",414,Survival,5,3
        |Force-Building Predator Style,-,Permanent,None,Permanent,"Your familiar permanently generates 1i/turn, except when DPM is active.",414,Survival,4,3
        |Crimson Talon Vigor,-,Permanent,None,Permanent,"When you land a Withering attack, your familiar gains half as much Initiative as you do.",414,Survival,5,3
        |Deadly Predator Method,"15m, 1wp ",Reflexive,None,Indefinite,"Your familiar becomes huge and intimidating. It can attempt huge feats of strength and gains major bonuses to movement actions. Its attacks will hit. Its Initiative does not reset. It CANNOT DIE, or even be wounded by Decisive attacks. If it Crashes, the Charm ends.",414,Survival,5,3
        |Precision of the Striking Raptor,1m ,Supplemental,Withering-only,Instant,"Calculate Accuracy on a Withering attack as if you were at Close range. At Close range, subtract 1 from target’s Defense.",415,Thrown,1,1
        |Steel Storm Descending,2m ,Supplemental,Decisive-only,Instant,"Cheap Excellency on your first attack if you roll the highest Join Battle. At E3, doesn’t reset your Initiative.",415,Thrown,2,1
        |Flashing Draw Mastery,3m ,Supplemental,None,Instant,"Take your first turn earlier, even though you don’t gain extra Initiative. Works cross-Ability.",415,Thrown,3,1
        |Joint-Wounding Attack,3m ,Supplemental,"Decisive-only, Stackable",Instant,"If your Decisive deals 3HL, the target takes -3 to all die pools for the scene.",415,Thrown,3,1
        |Angle-Tracing Edge,3m ,Simple,Uniform,Instant,Negate the target’s cover.,416,Thrown,4,1
        |Triple Distance Attack Technique,1m ,Supplemental,Uniform,Instant,Make Thrown attacks from Long range.,416,Thrown,3,1
        |Cascade of Cutting Terror,"5m, 1wp ",Reflexive,Decisive-only,Instant,"1/scene, make an undodgeable attack with a full Excellency that also strikes all targets nearby.",416,Thrown,4,1
        |Swarm-Culling Instinct,2m ,Supplemental,Uniform,Instant,"Reroll one nonsuccess for each 10 on a Join Battle. If you roll highest, attack (Dexterity) opponents.",416,Thrown,4,2
        |Mist on Water Attack,2m/turn ,Supplemental,"Decisive-only, Mute",Instant,Target struck by a Decisive attack can make no sound for several rounds.,416,Thrown,4,2
        |Observer-Deceiving Attack,3m ,Supplemental,"Mute, Uniform",Instant,"A thrown attack appears to come from a different direction. At E3, make thrown attacks from stealth without revealing yourself.",417,Thrown,4,2
        |Flying Steel Ruse,2m ,Supplemental,Decisive-only,Instant,"“Reroll 6s equal to your 7s” on a Distract or Disarm gambit, + bonus successes.",417,Thrown,5,2
        |Empty Palm Technique,-,Permanent,None,Permanent,"1/scene, keep your Initiative after disarming the target.",417,Thrown,5,2
        |Fallen Weapon Deflection,"3m, 2i ",Reflexive,None,Instant,Knock a dropped weapon one range band.,417,Thrown,5,2
        |Fallen Weapon Deflection x2,"3m, 2i ",Reflexive,None,Instant,Allows you to target an already fallen or dropped weapon.,417,Thrown,5,2
        |Mist-Gathering Practice,3m ,Reflexive,Mute,Instant,Bonus Initiative when you Aim to make a Decisive from cover or stealth.,417,Thrown,5,2
        |Shower of Deadly Blades,"6m, 1wp ",Simple,Withering-only,Instant,Your Withering attack strikes all enemies within short range of the target.,417,Thrown,5,2
        |Shrike Saving Discretion,-,Permanent,None,Instant,"When you use SCI to strike a target Decisively, you may retain extra Initiative equal to the 9s and 10s rolled.",418,Thrown,5,3
        |Crimson Razor Wind,"5m, 1wp ",Reflexive,"Decisive-only, Mute",Instant,"When you fool an opponent with ODA, you can make a Decisive ambush attack",418,Thrown,5,3
        |Sharp Hand Feint,"1m, 1wp ",Supplemental,Decisive-only,Instant,Automatically hit with a Distract attack.,418,Thrown,5,3
        |Shadow Wind Slash (Shadow Wind Kill),"2m or 1m, 1wp ",Simple,"Clash, Decisive-only",Instant,"Roll a Decisive attack or Disarm gambit twice, taking the better result.",418,Thrown,5,3
        |Shadow Thrust Spark,4m ,Reflexive,None,Instant,"When you hit with SHF, you also Disarm the target.",419,Thrown,5,3
        |Savage Wolf Attack,"5m, 1wp ",Reflexive,Withering-only,Instant,"When someone tries to recover a weapon you’ve Disarmed from him, you get a free unblockable, undodgeable Withering attack on him.",419,Thrown,5,3
        |Falling Icicle Strike,6m ,Supplemental,Decisive-only,Instant,Double damage on an ambush attack.,419,Thrown,5,3
        |Fiery Solar Chakram,"5m, 1wp, 1+a ",Simple,Decisive-only,Instant,"Throw an accurate, extra-damaging Kamehameha.",419,Thrown,5,3
        |Cutting Circle of Destruction,"5m, 1wp ",Simple,None,Instant,Throw an attack that strikes multiple enemies with Withering blows before reaching a final enemy with a Decisive.,419,Thrown,5,4
        |War God Descendent,3m ,Supplemental,None,Instant,Your army has +1 Size and ignores the penalty for poor drill troops.,420,War,1,1
        |Immortal Commander’s Presence,3m ,Simple,None,Instant,Train a crew to reroll nonsuccesses on an artillery weapon.,420,War,2,1
        |League of Iron Preparation,5m ,Simple,None,Indefinite,"Your troops are immune to mundane demoralization (as per the maneuver), as well as penalties for weather, food, etc.",420,War,3,1
        |Rout-Stemming Gesture,3m ,Reflexive,None,Instant,Make a reflexive rally action with bonus successes.,421,War,3,1
        |Holistic Battle Understanding,2m ,Supplemental,None,Instant,"When rolling for a stratagem, ignore penalties for unfamiliarity with the enemy general or army.",421,War,2,1
        |Ideal Battle Knowledge Prana,3m ,Supplemental,None,Instant,Double-9s or double-8s on an Order.,421,War,2,1
        |Tiger Warrior Training Technique,"10m, 1wp ",Simple,None,Indefinite,Grant your unit improved stats and drill; spend XP to grant them bonuses against select enemies or perfect morale.,421,War,4,2
        |Magnanimity of the Unstoppable Icon,"3m, 1wp ",Reflexive,None,Instant,Recover points of Magnitude equal to the 1s and 2s on an enemy rally for numbers roll.,421,War,4,2
        |Redoubt-Raising Gesture,1m ,Supplemental,None,Instant,Finish a strategic maneuver with one less success.,422,War,3,2
        |General of the All-Seeing Sun,4m ,Supplemental,None,Instant,"Excellency discount on a Strategic Maneuver; with sufficient successes, pull several maneuvers at once.",422,War,4,2
        |Immortal Warlord’s Tactic,"4m, 4i, 1wp ",Simple,None,Instant,Enact an uncounterable Strategic Maneuver with double-7s.,422,War,4,2
        |Battle Path Ascendant,5m ,Reflexive,None,Instant,"When your army empties the Magnitude of another army, you can roll Join Battle.",423,War,4,2
        |March of the Returner,"10m, 1wp ",Simple,None,Instant,"1/scene, make an automatically successful rally action.",423,War,5,3
        |Supremacy of the Divine Army,"10m, 1wp ",Reflexive,None,Instant,"Make a reflexive rally for numbers action, recruiting animals or nature itself to your side.",423,War,5,3
        |Four Glories Meditation,-,Permanent,None,Permanent,"Permanently, recurring 6s on a War roll.",423,War,5,3
        |Transcendent Warlord’s Genius,1m ,Reflexive,None,Instant,"When activating BPA, you can perform a maneuver with successes no higher than half the Initiative gained.",423,War,5,3
        |Battle-Visionary’s Foresight,"10m, 1wp ",Simple,None,Scene,"Choose two maneuvers; if the enemy chooses one of them, you automatically win the maneuver roll and can enact a stratagem of equal or fewer successes.",423,War,5,3
        |,,,,,,,,,
        |Hanging Judgement Arc,6m,Reflexive,Decisive-Only,Instant,,MotSE 5,Archery,4,2
        |Nova Arrow Attack,"6m, 1wp",Simple,Decisive-Only,Instant,,MotSE 6,Archery,5,3
        |Hell-Heeling Arrow,"13m, 1wp",Simple,Decisive-Only,Instant,,MotSE 6,Archery,5,4
        |Solar Judgement Flare,"3m, 1wp and 1m/die",Simple,Decisive-Only,Instant,,MotSE 6,Archery,5,5
        |Strength-Hying Heave,4m,Reflexive,None,Instant,,MotSE 7,Athletics,4,1
        |Faster than Self Technique,"10m, 1wp (+1m/rnd)",Simple,Perilous,Scene,,MotSE 8,Athletics,5,5
        |Swift Strike Prana,"3m, 1wp",Reflexive,Decisive-Only,Instant,,MotSE 8,Brawl,4,1
        |Heart-Eating Fist,"3a, 1wp",Simple,Decisive-Only,Instant,,MotSE 8,Brawl,5,5
        |Burning Sky Apocalypse Strike,"15m, 1wp",Simple,Decisive-Only,Instant,,MotSE 9,Brawl,5,5
        |Honor-Instilling Mantra,6m,Simple,Mute,Instant,,MotSE 9,Bureaucr.,5,2
        |Pattern-Exploiting Commerce Spirit,5m,Simple,Mute,Indefinite,,MotSE 9,Bureaucr.,5,3
        |Creation of Adamant Specie,5m,Simple,Mute,Instant,,MotSE 10,Bureaucr.,5,4
        |Spectacle-Inciting Order,8m,Simple,None,Instant,,MotSE 11,Bureaucr.,5,4
        |Ever-Ready Innovation Discipline,"15m, 1wp",Reflexive,None,Instant,,MotSE 11,Craft,5,3
        |Empathic Aegis Discipline,5m,Simple,None,Indefinite,,MotSE 12,Integrity,5,3
        |Strength From Conviction Stance,4m,Reflexive,Bridge,Instant,,MotSE 13,Integrity,5,3
        |Fetich-Training Eye,1m,Supplemental,None,Instant,,MotSE 14,Invest.,3,1
        |Truth-Rendering Attitude,3m,Simple,Mute,One Action,,MotSE 14,Invest.,4,2
        |Criminal Seduction Method,-,Permanent,None,Permanent,,MotSE 14,Larceny,3,2
        |Sun-Stealing Shadow Spirit,7m,Simple,Mute,Instant,,MotSE 15,Larceny,5,3
        |Soul-Drawing Pattern,"8m, 1wp",Simple,None,Instant,,MotSE 15,Linguistics,5,3
        |Indelible Spoken Script,5m (+1wp),Simple,Written-only,Indefinite,,MotSE 15,Linguistics,5,3
        |Soul-Light Spreading Discipline,-,Permanent,None,Permanent,,MotSE 16,Lore,5,2
        |Cloud-Wreathed Scholar,1m,Reflexive,None,Instant,,MotSE 16,Lore,5,3
        |Hundred Sages Focus,6m,Reflexive,Mute,Instant,,MotSE 16,Lore,5,3
        |Legend-Spirit Convocation,-,Permanent,None,Permanent,,MotSE 17,Lore,5,3
        |All-Knowing Enlightened Sovereign,"7m, 1wp",Simple,None,Instant,,MotSE 17,Lore,5,4
        |Time-Halting Flow,"15m, 1wp",Reflexive,None,Indefinite,,MotSE 18,Medicine,5,5
        |Nimble Reaving Wind,1m,Reflexive,Dual,Instant,,MotSE 18,Melee,1,1
        |Arc Shedding Rain Technique,3m,Reflexive,Uniform,Instant,,MotSE 18,Melee,3,1
        |Gleaming Sever,"1m, 1i",Reflexive,Uniform,Instant,,MotSE 19,Melee,4,1
        |Stark Truth of Steel Method,"2m, 1i",Reflexive,Uniform,Instant,,MotSE 19,Melee,4,2
        |Victorious Wreath,"6m, 1wp (+1i/rnd)",Reflexive,Perilous,Scene,,MotSE 19,Melee,5,3
        |Whirlwind-Tempest Deflection,"6m, 1wp",Reflexive,"Counterattack, Decisive-only, Perilous",Instant,,MotSE 19,Melee,5,3
        |Shattering Clash,5m,Reflexive,Decisive-only,Instant,,MotSE 20,Melee,5,3
        |Omniscient Focus Attack,"6m, 1wp",Reflexive,"Decisive-only, clash",Instant,,MotSE 21,Melee,5,3
        |All-Sundering Strike,"3m, 1wp",Supplemental,Clash,Instant,,MotSE 21,Melee,5,3
        |Divine Executioner Stance,6m(+1m),Simple,Uniform,Indefinite,,MotSE 21,Melee,5,3
        |Six Eternities' Travail,-(1wp),Permanent,None,Permanent,,MotSE 22,Occult,5,2
        |Anima-Suffused Spirit,"1m+2m,1a/hl",Reflexive,None,Instant,,MotSE 22,Occult,5,3
        |What Light Reveals,"6m, 1wp",Reflexive,None,Scene,,MotSE 22,Occult,5,3
        |Divine Instrument,1m or 2m,Reflexive,None,Instant,,MotSE 23,Perform.,4,2
        |Seventeen Cycles Symphony,"5m, 1wp",Reflexive,Mute,Instant,,MotSE 23,Perform.,5,3
        |Drama-Fueling Ardor,1m,Supplemental,Mute,Instant,,MotSE 24,Perform.,4,2
        |Divinely-Inspired Performance,5m,Reflexive,None,Instant,,MotSE 24,Perform.,5,3
        |Seven Thunders Voice,"5m, 1wp (6m, 1wp)",Simple,None,Scene,,MotSE 24,Perform.,5,3
        |Poised Lion Attitude,3m,Reflexive,None,Instant,,MotSE 25,Presence,4,1
        |Holy Touch,"6m, 1wp",Simple,Decisive-only,Instant,,MotSE 25,Presence,5,3
        |God-Heeling Gesture,4m,Reflexive,None,Instant,,MotSE 25,Presence,5,3
        |Unnerving Solar Presence,6m,Reflexive,Mute,Instant,,MotSE 26,Presence,5,3
        |Voice-Empowering Aspect,"6m (1m, 1wp)",Simple,None,Indefinite,,MotSE 26,Presence,5,4
        |Divinity-Conferring Touch,10m,Simple,None,Indefinite,,MotSE 27,Presence,5,5
        |Armed and Ready Discipline,3m,Reflexive,None,Instant,,MotSE 27,Resist.,2,1
        |Fortress-Body Discipline,"3m, 1wp",Reflexive,Dual,Scene,,MotSE 28,Resist.,5,4
        |Storm-Racing Destrier,2m,Reflexive,None,One Day,,MotSE 28,Ride,3,1
        |Sciff-To-Scow Method,5m,Simple,None,Indefinite,,MotSE 28,Sail,4,1
        |Ash and Storm Aegis,"8m, 1wp",Reflexive,None,One hour,,MotSE 28,Sail,5,3
        |Perfect Reckoning Technique,4m,Reflexive,None,Instant,,MotSE 29,Sail,5,3
        |Mast of Everything Situation,"10m, 1wp",Simple,Apocryphal,Scene,,MotSE 29,Sail,5,3
        |Death-Daring Maneuver,6m,Simple,"Apocryphal, Pilot",Instant,,MotSE 29,Sail,5,3
        |Master and Commander Method,"10m, 1wp",Reflexive,"Apocryphal, Pilot",Scene,,MotSE 30,Sail,5,3
        |Viper-Scenting Method,3m,Reflexive,None,Scene,,MotSE 30,Socialize,4,1
        |Energic Influence Technique,3m,Supplemental,None,Instant,,MotSE 31,Socialize,4,1
        |Rancor-Raising Spirit,3m,Supplemental,None,Instant,,MotSE 31,Socialize,5,1
        |Soul-Testing Method,"1m, 1wp",Reflexive,None,Instant,,MotSE 32,Socialize,5,2
        |Stalking Shadow Spirit,5m,Simple,Mute,Indefinite,,MotSE 32,Stealth,4,1
        |Killing Shroud Technique,"5m, 1wp",Reflexive,Mute,Instant,,MotSE 32,Stealth,5,2
        |Shadow-Striking Way,4m,Supplemental,"Decisive-only, Mute",Instant,,MotSE 33,Stealth,5,3
        |Harmony with Nature Approach,-,Simple,None,Instant,,MotSE 34,Survival,3,1
        |Elements-Sculpted Avatar,6m,Simple,None,Indefinite,,MotSE 34,Survival,5,3
        |Riotous Cry of the Beast,"8m, 1wp",Simple,None,Scene,,MotSE 34,Survival,5,3
        |Colossal Rampaging Beast,"7m, 3a, 1wp",Reflexive,None,Scene,,MotSE 35,Survival,5,4
        |Baara-Unleashing Technique,-,Reflexive,None,Instant,,MotSE 35,Survival,5,5
        |Spitting Hand Technique,1m,Reflexive,None,Instant,,MotSE 36,Thrown,3,1
        |Diving Hawk Discipline,3m,Supplemental,Decisive-only,Instant,,MotSE 36,Thrown,4,1
        |Thunder-Quelling Gesture,"3m, 2i",Reflexive,Decisive-only,Instant,,MotSE 36,Thrown,5,1
        |Death-Dealing Diffusion,"1m, 1wp",Simple,Decisive-only,Instant,,MotSE 36,Thrown,5,2
        |Whirlwind Hand of the Striker,"4m, 1wp",Simple,Decisive-only,Instant,,MotSE 37,Thrown,5,2
        |Dancing Steel Symphony,"9m, 1wp",Simple,Decisive-only,Instant,,MotSE 37,Thrown,5,3
        |Unstoppable Solar Conqueror,5m,Reflexive,None,Scene,,MotSE 38,War,3,1
        |One with Five Forces,5m,Reflexive,None,Instant,,MotSE 38,War,5,4
        |""")


  private final val evocationsCSV : String = {
    """Heart-Knowing Blade,3m,Supplemental,Decisive-only,Instant,,614,,,1
No Other Blade,1m,Supplemental,Uniform,Instant,,614,,,1
Magnanimous Sunfire Blast,1m/revealed Intimacy,Reflexive,Decisive-only,Instant,,615,,,2
Holy Miracle Strike,"10m, 1wp",Simple,Uniform,Instant,,615,,,3
Battle Dance of the Warrior Wed,-,Reflexive,Uniform,One combat,,615,,,3
Luminous Soul Warden,1m/1a,Reflexive,"Decisive-only, Stackable",One scene,,616,,,1
Dawn of a Hundred Rebukes,"5m, 1wp",Reflexive,Decisive-only,Instant,,617,,,2
Unconquered Guardian Defense,"4m, 3i",Simple,None,Instant,,617,,,3
Moonlit Huntress Aura,1wp,Reflexive,None,Three turns,,618,,,2
Winter Night Cut,"3m, 2i",Reflexive,Withering-only,Instant,,618,,,1
Frozen Soul Resolve,"1m, 1i",Reflexive,Perilous,Instant,,619,,,1
Cold Moon Slash,"6m, 2i, 1wp",Simple,"Decisive-only, Perilous",Instant,,619,,,2
Ice-Fixing Strike,"3m, 1wp",Simple,Withering-only,Instant,,619,,,3
Howling Lotus Strike,3m,Supplemental,Decisive-only,Instant,,620,,,1
Venom-Intensifying Stroke,3m,Supplemental,"Stackable, Uniform",Instant,,620,,,1
Seven Widows Venom,-,Permanent,None,Permanent,,620,,,2
Deadly Flowers Blooming,"2m, 2i",Reflexive,Stackable,One scene,,620,,,2
Delicate Crimson Execution,"5m, 1wp",Simple,Decisive-only,Instant,,621,,,3
Stepping Through Strife,-,Permanent,None,Permanent,,621,,,1
Snow-Gathering Elusion,3m,Reflexive,"Perilous, Stackable, Uniform",Instant,,622,,,1
Destination-Hunting Impulse,"5m, 1wp",Simple,Perilous,One scene,,622,,,2
Clear Path Defense,-,Permanent,Uniform,Permanent,,622,,,2
Pound the Drums,"6m, 1wp",Simple,Perilous,One scene,,622,,,3
Orichalcum Wings Elevation,2m,Supplemental,Uniform,Instant,,623,,,1
Cloud-Blending Camouflage,4m,Simple,None,Until cover is broken,,623,,,1
Drifting Hawk Tactics,3m,Reflexive,Uniform,Reflexive,,623,,,2
Golden Talon Strike,"3m, 1wp",Supplemental,Uniform,Instant,,623,,,2
Sun-Gilded Hawk Soul,"5m, 3a, 1wp",Simple,None,One scene,,624,,,3
Pounding Heart Triumph,"5m, 2i",Reflexive,Withering-Only,One scene,,624,,,1
Taste of Victory Rhythm,3m/successful attack,Reflexive,Withering-only,Until next turn,,625,,,2
Champion’s Fatal Strike,--,Supplemental,Decisive-only,Instant,,625,,,3
Daredevil Bravado Defense,7m,Reflexive,Decisive-only,Instant,,625,,,3
Life-Drinking Resurgence,-,Permanent,Decisive-only,Permanent,,626,,,1
Dark Life Detection,-,Permanent,Decisive-only,Permanent,,626,,,1
Seven Traumas Technique,"3m, 1+ blades",Supplemental,Decisive-only,Instant,,626,,,2
Errant Orbit Scythe,"4m, 1wp, 1+ blades",Simple,Withering-only,Instant,,626,,,3
Soul-Reaving Release,"5m, 1wp",Simple,Decisive-only,Instant,,627,,,4
Grand Eruption,10m,Simple,Uniform,Instant,,628,,,1
Pregnant Flame Exhumation,4m,Reflexive,Decisive-only,Instant,,628,,,2
Magma Burial,"4m, 2i, 1wp",Simple,"Decisive-only, Perilous",Instant,,629,,,2
Caldera Reconfiguration Stance,"6m, 3i, 1wp",Simple,"Decisive, Perilous",Instant,,629,,,3
Pyroclastic Holocaust Judgment,"12m, 1wp",Simple,"Decisive-only, Perilous",Instant,,629,,,4
"""
  }

  private final val martialArtsCSV : String = {
      """Serpentine Evasion,2m,Reflexive,"Mastery, Uniform",Instant,+1 Evasion; can be activated after attack if you have higher Initiative.,427,Snake,2,1
        |Striking Cobra Technique,3m,Supplemental,"Dual, Mastery",Instant,Bonus damage against slower enemies. Extra bonus damage if they’re crashed.,427,Snake,3,1
        |Snake Form,8m,Simple,Form,Scene,"Form: attacks on you take -1 die, or -3 if your Initiative is higher. Bonus soak.",427,Snake,4,1
        |Armor-Penetrating Fang Strike,"5m, 1wp",Supplemental,"Dual, Terrestrial",Instant,Ignore armor soak or some hardness.,428,Snake,5,2
        |Crippling Pressure-Point Strike,3m,Simple,"Decisive-only, Terrestrial",Instant,Perform gambits to blind or paralyze your enemy.,428,Snake,4,2
        |Essence Fangs and Scales Technique,"- (+1m, 1wp)",Permanent,Mastery,Permanent,"Upgrade Snake Form to give extra soak and Withering damage (and Decisive damage, with Mastery).",429,Snake,4,2
        |Uncoiling Serpent Prana,"7m, 3i",Simple,"Terrestrial, Uniform",Instant,Drag an enemy from Medium to Close range.,429,Snake,4,3
        |Snake Strikes the Heel,"2m, 1wp",Reflexive,"Clash, Dual, Terrestrial",Instant,Reflexively Clash with an enemy.,429,Snake,4,2
        |Countless Coils Evasion,"7m, 1wp",Reflexive,"Decisive-only, Perilous, Terrestrial",Instant,Use your Initiative to cancel damage from a Decisive attack.,429,Snake,5,3
        |Essence Venom Strike,"6m, 3i, 1wp",Simple,"Aggravated, Decisive-only, Mastery, Terrestrial",Instant,Poison your enemy with Aggravated damage-dealing venom.,430,Snake,5,3
        |Crimson Leaping Cat Technique,4m,Supplemental,"Uniform, Mastery",Instant,"Steal 1i from an enemy when you successfully Rush him. Extra Rush successes grant bonus dice to your next attack on that enemy, as long as it’s within the next round.",431,Tiger,3,1
        |Striking Fury Claws,6m,Supplemental,"Mastery, Withering-only",Instant,"On a Withering attack, some of your extra attack successes become post-soak damage. This is increased if your target tried to run, or if you’re a Solar.",431,Tiger,3,1
        |Tiger Form,"10m,",Simple,"Form, Terrestrial",Scene,Form. Some extra attack successes count double on a Withering roll. Fight prone without penalties. Bonus dice to Rush or counter Disengages; gain Initiative when the enemy Disengages.,431,Tiger,4,1
        |Celestial Tiger Hide,5m,Reflexive,Terrestrial,Scene,Gain soak and Hardness; drop this Charm to subtract Hardness from an incoming Decisive.,432,Tiger,5,2
        |Iron Claw Grip,5m,Reflexive,Mastery,Instant,Bonus rounds of control on a grapple.,432,Tiger,4,2
        |Prey-Maiming Frenzy,"8m, 1wp",Simple,"Decisive-only, Terrestrial",Instant,"Deal extra damage, and reset to higher Initiative, when you savage a grappled enemy.",432,Tiger,5,3
        |Raging Tiger Pounce,4m,Supplemental,"Mastery, Uniform",Instant,"Knock an enemy prone with any attack; steal Initiative if he tries to stand up. If Mastery, your attack on a prone enemy is undodgeable and does even more damage.",432,Tiger,4,2
        |Spine-Shattering Bite,"5m, 1wp",Reflexive,"Decisive-only, Terrestrial",Instant,"On a successful Decisive, trade damage for preventing your enemy from moving.",433,Tiger,5,3
        |Stalking Cat Movement Meditation,5m,Reflexive,Mastery,Scene,"For the scene, enemies at short or medium range must Disengage to move away from you and take -1 Defense vs. surprise attacks.",433,Tiger,4,2
        |Leap From Cloaking Shadows,"5m, 1wp",Supplemental,"Decisive-only, Mastery, Terrestrial",Instant,"Limited double-9s, double-8s, or double-7s on an attack roll.",433,Tiger,5,3
        |Angry Predator Frenzy,"- (+5m, 1wp)",Permanent,"Mastery, Terrestrial",Permanent,Upgrade Tiger Form. Some extra attack successes transfer to damage on Decisives. Rush reflexively. You can’t Disengage.,433,Tiger,5,3
        |Gathering Light Concentration,3m,Reflexive,"Mastery, Uniform",Instant,"When you parry an attack, give the Onslaught penalty to your attacker instead of taking it yourself.",434,Single Point,2,1
        |Shining Starfall Execution,6m,Supplemental,"Decisive-only, Mastery",Instant,"Add extra damage to a Decisive – more, if it’s a big Decisive – but risk losing more if your attack fails.",434,Single Point,3,1
        |Single Point Shining Into the Void Form,10m,Simple,"Form, Mastery, Terrestrial",Scene,Form. Your sword Joins Battle and makes its own attacks each turn.,434,Single Point,4,2
        |Fatal Stroke Flash,"1m, 1wp",Supplemental,"Decisive-only, Mastery, Terrestrial",Instant,"Gain extra damage on a Decisive if your Initiative is higher than your enemy’s, but lose extra Initiative if the attack fails.",435,Single Point,4,2
        |Liquid Steel Flow,5m (+1wp),Reflexive,"Dual, Mastery, Terrestrial",One round,"Scenelong extra damage to Withering attacks; you can end the Charm for a big hit with a Decisive (or two big hits, if your sword helps).",436,Single Point,5,2
        |Void-Slicing Wind,"6m, 1wp",Simple,"Decisive-only, Terrestrial",Instant,"Strike an enemy out to medium range, adding accuracy and damage the further out he is. Slice all opponents between you and him.",436,Single Point,5,3
        |Horizon-Swallowed Star Flash,"8m, 1wp",Reflexive,"Clash, Decisive-only, Mastery, Terrestrial",Instant,"Clash reflexively, albeit expensively. Deal extra damage on this Decisive.",437,Single Point,5,2
        |Six-Demon Scabbard Binding,"1m, 1wp (or 4m)",Reflexive,"Clash, Decisive-only, Terrestrial",Instant,Clash with shaping magic. Cut a path through the Wyld with your irresistible sword spirit.,437,Single Point,5,3
        |Blinding Nova Flare,-,Simple,"Decisive-only, Mastery, Terrestrial",Instant,"Make a gambit with no need for an attack roll. If it succeeds, make an undodgeable, unblockable Decisive attack.",437,Single Point,5,3
        |Falling Scythe Flash,5m,Supplemental,Dual,Instant,Add bonus damage based on Strength to a Withering or Decisive attack.,438,White Reaper,3,1
        |Revolving Crescent Defense,"4m, 1i",Reflexive,"Mastery, Uniform",Instant,Your Onslaught penalties turn into bonuses vs. one attack.,438,White Reaper,3,1
        |White Reaper Form,"8m,",Simple,Form,Scene,"Form. Double-10s on Decisive damage. -1 Defense to all battle groups. Move through battle groups. Collect halos that grant +1 Resolve each for crashing or killing notable enemies, or for damaging battle groups.",438,White Reaper,4,1
        |Bleeding Crescent Strike,5m,Supplemental,"Mastery, Terrestrial, Uniform",Instant,"Limited double-9s (or double-8s, vs. battle groups) on an attack roll. Expend halos for +1 die each.",439,White Reaper,4,2
        |Greatest Killer Attitude,"3m, 1wp",Supplemental,Dual,Instant,"Extra Withering or Decisive damage. Vs. a battle group, ignore soak from Size or add extra damage for high Initiative. Burn halos for post-soak Withering damage.",439,White Reaper,5,2
        |Enemies Like Grass,"10m, 1wp",Simple,"Dual, Terrestrial",Instant,"Make two Decisive attacks, or two Withering attacks on a battle group. Burn halos for +1 attack each.",440,White Reaper,5,3
        |Flickering Corona Barrier,2m,Reflexive,"Mastery, Uniform",Instant,"Add (halos) to Parry, or burn them for double that bonus.",440,White Reaper,3,2
        |Impenetrable White Shroud,"4m, 1wp",Reflexive,None,Scene,"Add (2 * [halos + 1]) to soak for the scene, or more if you aren’t wearing armor. Battle groups don’t add Size to damage.",440,White Reaper,4,2
        |Snow Follows Winter,- (+1wp),Permanent,Mastery,Permanent,"Burn halos to heal damage. For the scene, reduce wound penalties by (halos), increase difficulty of rout checks (I bet!), and expend halos for Initiative.",440,White Reaper,5,3
        |Nothing But Shadows,3m,Reflexive,"Decisive-only, Mastery",Instant,Penalize attempts to see you in combat. Fake your own death to escape battle.,441,Ebon Shadow,2,1
        |Seven Points of Weakness Strike,4m,Supplemental,"Mastery, Withering-only",Instant,"Subtract (Stealth), or (2*Stealth) in a surprise attack, from enemy’s armored soak.",441,Ebon Shadow,3,1
        |Ebon Shadow Form,8m,Simple,"Form, Mute",Scene,Form. Anyone who fails their check when you enter stealth loses 1i. Bonus Initiative when you reset after an unexpected Decisive. +1 Defense.,441,Ebon Shadow,4,1
        |Elusive Flicker Evasion,"4m, 1wp",Reflexive,"Decisive-only, Mastery, Terrestrial",Instant,"Enemy rolls a Decisive twice, taking the lower result.",442,Ebon Shadow,5,2
        |Shadow-Body Dissolution,10m,Reflexive,"Decisive-only, Mute, Terrestrial",Instant,Subtract (Essence + Stealth) from a Decisive aimed at you.,442,Ebon Shadow,5,2
        |Shadow-Stepping Motion,5m,Supplemental,"Dual, Mute",Instant,Move in shadows more easily; do extra damage if you use this movement to reach Close range on an enemy.,443,Ebon Shadow,4,2
        |Throat-Slitting Shadow Atemi,"1m, 1wp",Supplemental,"Decisive-only, Terrestrial",Instant,"Extra successes on a Decisive attack roll convert damage dice to successes – without limit, on a surprise attack.",443,Ebon Shadow,4,3
        |Umbra Noose Execution,"5m, 1wp",Simple,"Decisive-only, Mastery",Instant,"Strangle an enemy with his own shadow, doing extra damage.",443,Ebon Shadow,5,3
        |Empowering Justice Redirection,3m,Supplemental,"Mastery, Withering-only",Instant,A Withering attack is more accurate and damaging against someone who has attacked you (or a Ward you were Defend Othering).,443,Crane,2,1
        |Fluttering Cry of Warning,3m,Reflexive,"Mastery, Uniform",Until next turn,+2 Parry when Defending Other.,444,Crane,2,1
        |Crane Form,8m,Simple,Form,Scene,Form. +1 Parry. Cost for Full Defense reduced by 1i. Respond to any attack on you or your ward with a counterattack.,444,Crane,4,2
        |Crossed Wings Denial,"6m, 4i",Reflexive,"Decisive-only, Terrestrial",Until next turn,Make a roll; bank successes for later use in raising Parry or making counterattacks.,444,Crane,5,2
        |Feather-Stirred Arrow Deflection,2m (+1wp),Reflexive,"Terrestrial, Uniform",Instant,Counterattack out to the range of the attack made on you.,444,Crane,5,3
        |Wings Spread to Sky,4m,Reflexive,Dual,Instant,"Fly for 1-2 actions, reducing falling damage at the end if still airborne. Double-10s on Decisive damage rolls.",445,Crane,5,3
        |Humbling Enlightenment Commentary,"1m, 1wp",Supplemental,"Decisive-only, Mastery, Terrestrial",Instant,"Add stunt dice to Decisive damage rolls. If you do enough damage, create Ties in the target.",445,Crane,4,2
        |Kindly Sifu’s Quill,5m,Supplemental,"Decisive-Only, Mastery",Instant,Double-10s on Decisive damage. Enemy’s Defense is penalized by his Intimacies as per Resolve.,445,Crane,5,2
        |Mournful Crane’s Cry,3m,Supplemental,Decisive-only,Instant,"Your counterattack throws an enemy two range bands, or disarms at a discount, or returns an attack to the attacker.",445,Crane,4,2
        |Wisdom of the Celestial Crane,"7m, 1wp",Reflexive,"Decisive-only, Mastery, Terrestrial",Instant,"When you Decisively counterattack your enemy’s Decisive attack, add his Initiative to your own.",446,Crane,5,4
        |Voice of the Night Bird,-,Permanent,Mastery,Permanent,"Your shouts are light ranged weapons, permanently. You can flurry them with Performance rolls at no penalty.",447,Silver-Voice Nigh.,2,1
        |Inspiring Battle Hymn,5m,Supplemental,Mastery,Instant,Reroll (but not recurring reroll?) all 1s on Join Battle for you and all allies who can hear you.,447,Silver-Voice Nigh.,3,1
        |Terrifying Battle Shriek,4m,Simple,Mastery,Instant,"Inspire fear in your enemy, forcing him to move away from you. If you also hit him with your kiai, treat him as if he had Intimacies of fear for you.",447,Silver-Voice Nigh.,3,1
        |Silver-Voiced Nightingale Form,8m,Simple,Form,Scene,Form. +1 Evasion. +Performance to your Withering damage. Gain 3i when someone pays WP to resist your influence.,448,Silver-Voice Nigh.,4,1
        |Hearing the Heart’s Song,"5m, 2i",Reflexive,"Mastery, Perilous, Decisive-Only",Instant,"+(Performance/2) to Evasion vs. a Decisive. If successful, reflexively Read Intentions with bonus dice.",448,Silver-Voice Nigh.,4,2
        |Harmony in Opposition Stance,"4m, 1wp",Reflexive,Terrestrial,Scene,"Against one enemy, you take no Evasion penalties, gain double-9s to Rush or Disengage, and gain 1i when he gains Initiative.",448,Silver-Voice Nigh.,5,2
        |Haunting Heart-Rending Melody,"1m, 1wp",Simple,Terrestrial,Instant,Make a Performance roll vs. all enemies within earshot; those overcome must pay 1wp or take -3 Defense from despair.,448,Silver-Voice Nigh.,4,2
        |Resounding Songbird’s Cry,"3m, 2i",Simple,Terrestrial,Instant,All enemies in short range are hit by a sonic environmental attack; all who fail to resist can be hit by an extra-accurate Withering kiai the next turn.,449,Silver-Voice Nigh.,5,3
        |Flashing Blade Harmony,4m,Reflexive,"Dual, Mastery",Instant,An ally’s attacks gain double-10s (Decisive) or double-9s (Withering) on damage. Bonus damage if you then attack the same target with your kiai.,449,Silver-Voice Nigh.,4,2
        |Aria of Victory,"8m, 1wp",Simple,"Perilous, Terrestrial",Scene,"You and all allies gain 1 WP each turn, which cannot be banked or spent on Charms. Wow!",449,Silver-Voice Nigh.,5,3
        |Shattering Discord Cacophony,"10m, 1wp",Simple,"Decisive-only, Mastery, Terrestrial",Until the enemy’s next turn,Make a kiai gambit on the target. Each success delays his turn by 1 tick and adds 1i to the damage of all Decisives made on him until his next turn.,450,Silver-Voice Nigh.,5,3
        |Blossom of Inevitable Demise,3m,Supplemental,Dual,Instant,Double-10s on Decisive damage. Use firewands out to medium range.,450,Righteous Devil,3,1
        |Cloud of Ebon Devils,2m,Supplemental,Mastery,Instant,Your Aim actions also allow you to reload your weapon.,450,Righteous Devil,2,1
        |Kiss of the Sun Concentration,3m,Supplemental,"Dual, Mastery",Instant,"Add your Aim dice to a Withering attack’s damage, or one die to a Decisive’s damage.",450,Righteous Devil,2,1
        |Righteous Devil Form,5m,Simple,Form,Scene,Form. Intimidate all enemies; anyone affected takes a penalty to run or hide. Reflexively Aim at anyone affected. Your firewand is a melee weapon.,451,Righteous Devil,3,2
        |Azure Abacus Meditation,"4m, 1wp",Supplemental,"Terrestrial, Withering-only",Instant,"Remove some or all of the target’s soak, depending on how much cover he has.",451,Righteous Devil,5,2
        |Burning Judgment Halo,"5m, 4i",Simple,"Perilous, Terrestrial",Instant,Everyone at close range is treated as standing in a bonfire.,451,Righteous Devil,4,3
        |Phoenix Flies on Golden Wings,3m,Supplemental,Dual,Instant,Cheap Withering or Decisive damage booster.,452,Righteous Devil,4,2
        |Dancing Devil Trigger Finger,"10m, 3i, 1wp",Simple,"Decisive-only, Perilous, Terrestrial",Instant,"Hit up to 6 enemies in a 90-degree arc, doing full Decisive damage to each(!).",452,Righteous Devil,5,3
        |"Caress of 1,000 Hells","6m, 1wp",Supplemental,"Aggravated, Decisive-only, Terrestrial",Instant,Target repents of his wicked deeds or takes [Essence]A levels of damage.,452,Righteous Devil,5,3
        |Open Palm Caress,4m,Supplemental,Mastery,Instant,"If anyone beats you at Join Battle, he is perceived as starting the fight – both by others and by himself, unless he beats your Guile.",453,Black Claw,2,1
        |Torn Lotus Defense,5m,Reflexive,"Counterattack, Mastery, Terrestrial, Uniform",Instant,"When attacked, counterattack socially to create positive Ties to yourself and steal Initiative. With the form active, also affects bystanders.",453,Black Claw,3,1
        |Flexing the Emerald Claw,5m,Supplemental,Decisive-only,Instant,Poison your enemy with a long-lasting Initiative-draining venom. Gain the Initiative it drains.,454,Black Claw,3,1
        |Black Claw Form,6m,Simple,Form,Scene,Form. +1 Evasion; disengage more easily and at lower cost. Penalize enemy defenses if they have positive Ties to you.,454,Black Claw,4,1
        |Doe Eyes Defense,"4m, 1i",Reflexive,"Mastery, Terrestrial, Uniform",Instant,An attack on you loses (Guile + positive Tie strength) dice.,455,Black Claw,5,2
        |Storm-Calming Embrace,"2m, 1wp",Reflexive,"Decisive-only, Mastery",Until released,"Grab and hold an enemy without savaging him. If he is poisoned, turns of grappling don’t count against the poison’s duration.",455,Black Claw,4,2
        |Table-Turning Reversal,6m,Reflexive,"Counterattack, Decisive-only",Instant,"Counterattack with a Disarm gambit, stealing your enemy’s weapon. It is a Black Claw style weapon as long as you carry it.",455,Black Claw,4,2
        |Outrage-Kindling Cry,"10m, 1wp",Reflexive,"Counterattack, Terrestrial",Instant,"When attacked Decisively, counterattack with social influence against everyone in earshot, subtracting your wound penalties from Resolves. Anyone affected turns on the attacker – violently, with appropriate Ties to you.",455,Black Claw,5,3
        |Heart-Ripping Claw,"10m, 1wp",Simple,"Aggravated, Decisive-only,","Mastery,Terrestrial Instant",Rip out your enemy’s heart. Double damage if he likes you; triple damage if he loves you.,456,Black Claw,5,3
        |Demure Carp Feint,3m,Reflexive,"Mastery, Uniform",Instant,"+1 Evasion, or +1 success to Disengage. +2 if your Appearance is higher than their Resolve. +1i if you succeed.",456,Dreaming Pearl,3,1
        |Elegant Weapon Repertoire,3m,Supplemental,"Dual, Mastery",Instant,"Add weapon’s accuracy Withering damage, or treat a mundane object as a light weapon.",457,Dreaming Pearl,3,1
        |Pearlescent Filigree Defense,1m,Reflexive,Mastery,Scene,Your clothes are light armor. Spend motes for temporary bonus soak.,457,Dreaming Pearl,2,1
        |Dreaming Pearl Courtesan Form,8m,Simple,Form,Scene,Form. EWR and PFD grant artifact weapons or armor. Make attacks from Short range.,457,Dreaming Pearl,4,2
        |Flurry of August Leaves,"3m, 1wp",Supplemental,Decisive-only,Instant,Your weapon tags are more effective than normal.,457,Dreaming Pearl,4,2
        |Vindictive Concubine’s Pillow Book,7m,Reflexive,"Decisive-only, Terrestrial",Instant,"Decisive damage roll gains anywhere from double-10s to double-7s, depending on how many triggers are satisfied. Activated after attack succeeds.",458,Dreaming Pearl,5,3
        |Fragrant Petal Fascination Kata,4m,Supplemental,None,Instant,"Bonus successes to entice your enemy. Steal 1i in combat, if you succeed. Triggers VCP.",458,Dreaming Pearl,4,2
        |Seven Storms Escape Prana,"4m, 2i",Reflexive,Mastery,Instant,"–(Appearance) to attempts to Rush, stop a Disengage, or control a grapple. Triggers VCP.",458,Dreaming Pearl,4,2
        |Invoking the Chimera’s Coils,"- (+8m, 1wp)",Permanent,"Mastery, Terrestrial",Permanent,"Turn into a sort of deer-giraffe-gazelle-carp thing. Yes, really. Fly. Enhance all other Charms of this style in substantial ways. Fade from reality if you take any damage to your -4 level. Yes, this means you’re dead.",458,Dreaming Pearl,5,3
        |Naked Fang Draw,5m,Supplemental,None,Instant,+(2 or Essence) successes to a Join Battle roll. You can move Initiative to Charge. +1 Charge if you rolled highest.,461,Steel Devil,2,1
        |Double Attack Technique,3m,Simple,"Terrestrial, Withering-only",Instant,"Make a Withering attack. If you roll enough extra successes to hit the target again, add (Dex + Charge/2) to damage.",461,Steel Devil,3,1
        |Triple Attack Technique,-,Permanent,"Mastery, Terrestrial, Withering-only",Permanent,"When using DAT, if you roll enough successes to hit the target a third time, increase bonus damage to (Dex + Charge).",462,Steel Devil,5,3
        |Steel Devil Strike,"5m, 1wp",Reflexive,"Decisive-only, Mastery",Instant,"After using DAT or TAT to deal significant damage, make a reflexive Decisive with bonus successes, spending only the Initiative you collected by using DAT or TAT.",462,Steel Devil,4,1
        |Steel Devil Form,7m,Simple,Form,Scene,Form. +2 to your Charge capacity. +1 to your Charge capacity per unique opponent you Crash. No penalties to your off-hand attack. DAT and SDS are cheaper.,462,Steel Devil,5,1
        |Seconds Between Strife,"4m, 1 charge per success",Supplemental,None,Instant,"+1 success on a Rush per point of Charge spent. If Rush succeeds, Charge is refunded.",462,Steel Devil,5,3
        |Sonic Slash,"5m, 1wp",Reflexive,"Decisive-only, Terrestrial",Instant,"After successfully using SBS, if the opponent retreats, make a Decisive that hits all enemies in front out to Medium range, with damage equal to your Charge. Does not expend Charge.",462,Steel Devil,5,3
        |Dual-Slaying Stance,"5m, 1wp",Simple,"Form, Terrestrial",Scene,"Form. +2 Parry. Your Parry cannot drop below 2. If you do not attack on your turn, you may make Decisive counterattacks with a mix of Charge and initiative.",463,Steel Devil,5,2
        |Twin-Blade Defense,"3m, 1 charge per success",Reflexive,None,Instant,Spend Charge after an attack hits to add extra bonuses to your Parry.,463,Steel Devil,5,3
        |Iron Lotus Unfurling,"5m, 1wp",Reflexive,"Counterattack, Decisive-only",Instant,"After using TBD with at least 3 Charge left, reflexively Disarm your enemy. If successful, use Sonic Slash.",464,Steel Devil,5,3
        |Empty Mind Strike,"4m, 1wp",Reflexive,Decisive-only,Instant,"As per SDS, but spending Charge instead of Initiative.",464,Steel Devil,5,2
        |Dervish Blade Frenzy,"12m, 1wp",Reflexive,"Mastery, Withering-only",Instant,"On using EMS, +1 Charge, and use TAT reflexively with bonus damage. If Solar, this activation of TAT allows a repeated use of SDS or EMS.",464,Steel Devil,5,3
        |Whirling Guillotine Dance,"10m, 1wp",Simple,"Form, Mastery",Scene,Fuse SDF and DSS.,464,Steel Devil,5,3
        |"""
  }

  private final val sorceryCSV : String = {
      """Terrestrial Circle,Cost,Keywords,Duration,Page No.
        |Cirrus Skiff,"15sm, 1wp",None,Until Ended,471
        |Corrupted Words,"15sm, 1wp",Psyche,Indefinite,472
        |Death of Obsidian Butterflies,"15sm, 1wp","Decisive-Only, Perilous",Instant,472
        |Demon of the First Circle,"Ritual, 2wp",None,Instant,473
        |Flight of the Brilliant Raptor,"15sm, 1wp","Decisive-Only, Perilous",Instant,473
        |Infallible Messenger,"5sm, 2wp",None,Until message is delivered,474
        |Invulnerable Skin of Bronze,"20sm, 1wp",None,One Day,474
        |Mists of Eventide,"7sm, 2wp",None,Three rounds,474
        |Silent Words of Dreams and Nightmares,"Ritual, 1wp",None,One dream,475
        |Stormwind Rider,"15sm, 1wp",None,One hour,475
        |Summon Elemental,"Ritual, 2wp",None,Instant,476
        |Wood Dragon's Claws,"5sm, 1wp",None,Until dismissed,476
        |,,,,
        |,,,,
        |,,,,
        |,,,,
        |Celestial Circle,Cost,Keywords,Duration,Page No.
        |Cantata of Empty Voices,"15sm, 2wp",Perilous,Instant or until ended,476
        |Demon of the Second Circle,"Ritual, 3wp",None,Instant,477
        |Impenetrable Veil of Night,"30sm, 1wp",None,One day,477
        |Incomparable Body Arsenel,"30sm, 2wp",None,(Essence) hours,477
        |Ivory Orchid Pavilion,"Ritual, 1wp",None,One day,478
        |Magma Kraken,"30sm, 1wp",None,One scene,478
        |Shadows of the Ancient Past,"10sm, 2wp",None,One scene,480
        |Travel Without Distance,"25sm, 2wp",Perilous,Instant,480
        |,,,,
        |,,,,
        |,,,,
        |,,,,
        |,,,,
        |Solar Circle,Cost,Keywords,Duration,Page No.
        |Benediction of Archgenesis,"Ritual, 3wp",None,Instant,480
        |Death Ray,"25sm, 2wp","Aggravated, Decisive-Only, Perilous",Instant or until ended,481
        |Demon of the Third Circle,"Ritual, 4wp",None,Instant,482
        |Rain of Doom,"40sm, 3wp",Aggravated,Until sunrise,483
        |"""
  }


  val solarCharms : IndexedSeq[Charms.ListedSolarCharm] = {
    solarCharmsCSV.flatMap(p => p.lines.map(l => {
      val token = l.split(",")
      assert(token.size == 10)
      Charms.ListedSolarCharm(name = token(0), cost = token(1), typ = token(2), keywords = token(3).split(' ').map(_.trim).toSet, duration = token(4), description = token(5), page = token(6), ability = token(7), abilityRating = Integer.parseInt(token(8)), essenceRating = Integer.parseInt(token(9)))
    })).toIndexedSeq
  }



}
