# The Great TODO List

This player allows players to set up `/worksite [name]` ports via `/setworksite [name]`.

TODO to make this plugin absolutely awesome:

- Allow setting up different number of worksites per-player via permissions `worksites.multiple.NUMBER`.
- Allow setting up public worksite for all players to use `/setworksite name public`
- Allow setting up private worksite for only certain players to use `/setworksite name permission.to.check`
- Allow setting up payment for use of `/setworksite` or `/worksite` commands
- Allow setting up cooldown for use of either `/setworksite` or `/worksite` commands

- Allow resetting cooldown to players (based on permissions | price)
    - `/worksiteresetcd [player:worksite]` - to be used by privileged player to reset cd for someone else (no fee)
    - `/worksiteresetcd worksite` - to be used by player to pay for cd reset

MVP update: No `/command PlayerName:WorkSiteNames` -> due to the "data storage design" and caching practice, this became 
way more problematic to implement. So I will not include it in minimum viable product. Later, when I reiterate on storage(s)
classes for plugin template, I will rework this.

Technical concepts to explore to get better

Additional MVP req: Listing your homes

Finishing touches:
  - DONE list your homes 
  - DONE autocomplete for worksite/delworksite

- Hooking into Vault to use installed Economy && Permissions (if needed) -> not needed in the end
- Expand basic command structure template for intellij
- Caching and updating of cache on playerjoin/playerleaves (write-through on important updates)

## Player worksite data file - concept

````yaml
name: DeadPix
sites:
  _default: 
    location: serialized_location_object_reserved_for_unnamed_worksite
    last_used: timestamp
    is_public: True|False
    permission: None|permission.name.to.check
  worksitename1: serialized_location_object
    location: serialized_location_object_reserved_for_unnamed_worksite
    last_used: timestamp
    is_public: True|False
    permission: None|permission.name.to.check 
````