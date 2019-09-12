alter table dudos.payment_payer
    add invoice_content_type varchar;

alter table dudos.payment_payer
    add invoice_content_data bytea;

insert into dudos.templates (id, body) values (12, convert_from(decode('PCFkb2N0eXBlIGh0bWw+CjwjYXNzaWduIG1ldGFkYXRhID0gcGF5bWVudFBheWVyLm1ldGFkYXRhLmdldERhdGFWYWx1ZSgpPgo8I2lmIG1ldGFkYXRhP2hhc19jb250ZW50PgogICAgPCNhc3NpZ24ga2ViTWV0YWRhdGE9bWV0YWRhdGE/ZXZhbC8+CjwjZWxzZT4KICAgIDwjYXNzaWduIGtlYk1ldGFkYXRhPScnLz4KPC8jaWY+CjxodG1sIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hodG1sIiB4bWxuczp2PSJ1cm46c2NoZW1hcy1taWNyb3NvZnQtY29tOnZtbCIgeG1sbnM6bz0idXJuOnNjaGVtYXMtbWljcm9zb2Z0LWNvbTpvZmZpY2U6b2ZmaWNlIj4KPGhlYWQ+CiAgICA8bWV0YSBjaGFyc2V0PSJVVEYtOCI+CiAgICA8bWV0YSBodHRwLWVxdWl2PSJYLVVBLUNvbXBhdGlibGUiIGNvbnRlbnQ9IklFPWVkZ2UiPgogICAgPG1ldGEgbmFtZT0idmlld3BvcnQiIGNvbnRlbnQ9IndpZHRoPWRldmljZS13aWR0aCwgaW5pdGlhbC1zY2FsZT0xIj4KICAgIDx0aXRsZT5SQkttb25leTog0KPRgdC/0LXRiNC90YvQuSDQv9C70LDRgtC10LY8I2lmIHBheW1lbnRQYXllci5zaG9wVXJsPz8+INC90LAg0YHQsNC50YLQtSAke3BheW1lbnRQYXllci5zaG9wVXJsfTwvI2lmPjwvdGl0bGU+CiAgICA8c3R5bGUgdHlwZT0idGV4dC9jc3MiPgogICAgICAgIGh0bWwsCiAgICAgICAgYm9keSB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICM2ODViZmY7CiAgICAgICAgICAgIG1pbi1oZWlnaHQ6IDEwMCU7CiAgICAgICAgICAgIG1pbi13aWR0aDogNDAwcHg7CiAgICAgICAgICAgIHdpZHRoOiAxMDAlOwogICAgICAgICAgICBtYXJnaW46IDA7CiAgICAgICAgICAgIHBhZGRpbmc6IDA7CiAgICAgICAgfQoKICAgICAgICAqIHsKICAgICAgICAgICAgZm9udC1mYW1pbHk6ICdSb2JvdG8nLCAnSGVsdmV0aWNhIE5ldWUnLCAnU2Vnb2UgVUknLCAnVmVyZGFuYScsICdUYWhvbWEnLCBzYW5zLXNlcmlmOwogICAgICAgIH0KCiAgICAgICAgLm92ZXJsYXlfY29udGFpbmVyIHsKICAgICAgICAgICAgYmFja2dyb3VuZC1jb2xvcjogIzY4NWJmZjsKICAgICAgICAgICAgbWluLWhlaWdodDogMTAwJTsKICAgICAgICAgICAgbWluLXdpZHRoOiA0MDBweDsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgICAgIG1hcmdpbjogMDsKICAgICAgICAgICAgcGFkZGluZzogMDsKICAgICAgICAgICAgYm9yZGVyLXNwYWNpbmc6IDA7CiAgICAgICAgfQoKICAgICAgICAubWFpbl9jb250YWluZXIgewogICAgICAgICAgICBtYXgtd2lkdGg6IDM0MHB4OwogICAgICAgICAgICBtYXJnaW46IGF1dG87CiAgICAgICAgICAgIHBhZGRpbmc6IDE4cHggNXB4IDE4cHggNXB4OwogICAgICAgIH0KCiAgICAgICAgLm1haW5fbG9nb19jb250YWluZXIgewogICAgICAgICAgICBwYWRkaW5nLWxlZnQ6IDIwcHg7CiAgICAgICAgfQoKICAgICAgICAuY29udGVudF90YWJsZSB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICNmZmZmZmY7CiAgICAgICAgICAgIGJvcmRlci1yYWRpdXM6IDdweDsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgICAgIGJvcmRlci1zcGFjaW5nOiAwOwogICAgICAgICAgICBwYWRkaW5nOiAxNXB4IDIwcHg7CiAgICAgICAgfQoKICAgICAgICAuY29udGVudF9tYWluX2hlYWRlciB7CiAgICAgICAgICAgIGNvbG9yOiAjMzhjZDhmOwogICAgICAgICAgICBmb250LXNpemU6IDI2cHg7CiAgICAgICAgICAgIHBhZGRpbmctdG9wOiAxOHB4OwogICAgICAgICAgICBoZWlnaHQ6IDM4cHg7CiAgICAgICAgICAgIG1hcmdpbjogYXV0bzsKICAgICAgICAgICAgbGluZS1oZWlnaHQ6IDMwcHg7CiAgICAgICAgICAgIHRleHQtYWxpZ246IGNlbnRlcjsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X21haW5fZGVzY3JpcHRpb24gewogICAgICAgICAgICBjb2xvcjogIzM1MzUzNTsKICAgICAgICAgICAgZm9udC1zaXplOiAxMXB4OwogICAgICAgICAgICBsaW5lLWhlaWdodDogMTFweDsKICAgICAgICAgICAgbWFyZ2luOiAwIGF1dG87CiAgICAgICAgICAgIHRleHQtYWxpZ246IGNlbnRlcjsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X21haW5fZGVzY3JpcHRpb25fbGluayB7CiAgICAgICAgICAgIHBhZGRpbmctdG9wOiA1cHg7CiAgICAgICAgICAgIGhlaWdodDogNDBweDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X2RpdmlkZXIgewogICAgICAgICAgICBib3JkZXItdG9wOiAxcHggc29saWQgI2MzYzNjMzsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgICAgIGhlaWdodDogMTVweDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X2lubmVyX3RhYmxlIHsKICAgICAgICAgICAgaGVpZ2h0OiA1MHB4OwogICAgICAgICAgICBib3JkZXItc3BhY2luZzogMDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X3NtYWxsX2hlYWRlciB7CiAgICAgICAgICAgIHBhZGRpbmc6IDA7CiAgICAgICAgICAgIHZlcnRpY2FsLWFsaWduOiBib3R0b207CiAgICAgICAgICAgIGNvbG9yOiAjYzNjM2MzOwogICAgICAgICAgICBmb250LXNpemU6IDEwcHg7CiAgICAgICAgICAgIGZvbnQtd2VpZ2h0OiA3MDA7CiAgICAgICAgICAgIHRleHQtdHJhbnNmb3JtOiB1cHBlcmNhc2U7CiAgICAgICAgICAgIHdpZHRoOiA3MCU7CiAgICAgICAgfQoKICAgICAgICAuY29udGVudF9zbWFsbF9oZWFkZXJfcmlnaHQgewogICAgICAgICAgICB3aWR0aDogMzAlOwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfc21hbGxfdGV4dCB7CiAgICAgICAgICAgIHBhZGRpbmc6IDA7CiAgICAgICAgICAgIHZlcnRpY2FsLWFsaWduOiB0b3A7CiAgICAgICAgICAgIGNvbG9yOiAjMzUzNTM1OwogICAgICAgICAgICBmb250LXNpemU6IDE0cHg7CiAgICAgICAgICAgIGZvbnQtd2VpZ2h0OiA3MDA7CiAgICAgICAgfQoKICAgICAgICAuY29udGVudF9zbWFsbF9sYWJlbCB7CiAgICAgICAgICAgIGJvcmRlci1yYWRpdXM6IDNweDsKICAgICAgICAgICAgYm9yZGVyOiAxcHggc29saWQgI2MzYzNjMzsKICAgICAgICAgICAgY29sb3I6ICNjM2MzYzM7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMTJweDsKICAgICAgICAgICAgdGV4dC10cmFuc2Zvcm06IHVwcGVyY2FzZTsKICAgICAgICB9CgogICAgICAgIC5zdXBwb3J0X3RleHQgewogICAgICAgICAgICBmb250LXNpemU6IDlweDsKICAgICAgICAgICAgY29sb3I6ICNhN2I5Zjk7CiAgICAgICAgICAgIHBhZGRpbmc6IDAgNXB4OwogICAgICAgIH0KCiAgICAgICAgLnN1cHBvcnRfbGluayB7CiAgICAgICAgICAgIGNvbG9yOiAjYTdiOWY5OwogICAgICAgICAgICB0ZXh0LWRlY29yYXRpb246IG5vbmU7CiAgICAgICAgfQoKICAgICAgICAuc3VwcG9ydF9lbWFpbF9saW5rIHsKICAgICAgICAgICAgY29sb3I6ICNhN2I5Zjk7CiAgICAgICAgICAgIHRleHQtZGVjb3JhdGlvbjogbm9uZTsKICAgICAgICAgICAgZm9udC13ZWlnaHQ6IGJvbGQ7CiAgICAgICAgfQoKICAgICAgICAucmlnaHQgewogICAgICAgICAgICB0ZXh0LWFsaWduOiByaWdodDsKICAgICAgICB9CgogICAgICAgIC53XzEwMCB7CiAgICAgICAgICAgIHdpZHRoOiAxMDAlOwogICAgICAgIH0KCiAgICAgICAgLmxpbmsgewogICAgICAgICAgICBjb2xvcjogIzY4NWJmZjsKICAgICAgICAgICAgdGV4dC1kZWNvcmF0aW9uOiBub25lOwogICAgICAgIH0KCiAgICAgICAgLmFtb3VudCB7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMThweDsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cgo8Ym9keT4KCjx0YWJsZSBjbGFzcz0ib3ZlcmxheV9jb250YWluZXIiPgogICAgPHRyPgogICAgICAgIDx0ZD4KICAgICAgICAgICAgPGNlbnRlcj4KICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0ibWFpbl9jb250YWluZXIiPgogICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJtYWluX2xvZ29fY29udGFpbmVyIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxpbWcgaGVpZ2h0PSIzNHB4IiB3aWR0aD09IjY3cHgiIHNyYz0iaHR0cDovL3Jiay5tb25leS9tYWlsL2ltZy9sb2dvLnBuZyIgY2xhc3M9Im1haW5fbG9nbyIgYWx0PSJSQksubW9uZXkgbG9nbyI+CiAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF90YWJsZSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgYWxpZ249ImNlbnRlciI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8aDMgY2xhc3M9ImNvbnRlbnRfbWFpbl9oZWFkZXIiPtCj0YHQv9C10YjQvdGL0Lkg0L/Qu9Cw0YLQtdC2PC9oMz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxoNSBjbGFzcz0iY29udGVudF9tYWluX2Rlc2NyaXB0aW9uIj7QkdC70LDQs9C+0LTQsNGA0LjQvCDQt9CwINC+0L/Qu9Cw0YLRgyDQt9Cw0LrQsNC30LAg0L3QsCDRgdCw0LnRgtC1PC9oNT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwjaWYgcGF5bWVudFBheWVyLnNob3BVcmw/Pz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxoNSBjbGFzcz0iY29udGVudF9tYWluX2Rlc2NyaXB0aW9uIGNvbnRlbnRfbWFpbl9kZXNjcmlwdGlvbl9saW5rIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8YSBocmVmPSIke3BheW1lbnRQYXllci5zaG9wVXJsfSIgY2xhc3M9ImxpbmsiPiR7cGF5bWVudFBheWVyLnNob3BVcmx9PC9hPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC9oNT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9kaXZpZGVyIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD48L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0KHRg9C80LzQsDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD48L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCBhbW91bnQiPiR7Zm9ybWF0dGVkQW1vdW50fTwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXJfcmlnaHQgcmlnaHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHNwYW4gY2xhc3M9ImNvbnRlbnRfc21hbGxfbGFiZWwiPiZuYnNwOyZuYnNwO9Ce0L/Qu9Cw0YfQtdC90L4mbmJzcDsmbmJzcDs8L3NwYW4+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QndC+0LzQtdGAINC40LfQstC10YnQtdC90LjRjzwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0Ij4ke3BheW1lbnRQYXllci5pbnZvaWNlSWR9PC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90YWJsZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCU0LDRgtCwPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCEtLSA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyX3JpZ2h0IGNvbnRlbnRfc21hbGxfaGVhZGVyIHJpZ2h0Ij5UaW1lPC90ZD4gLS0+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0Ij4ke3BheW1lbnRQYXllci5kYXRlLmZvcm1hdCgnZGQuTU0ueXl5eScpfTwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwhLS0gPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlcl9yaWdodCBjb250ZW50X3NtYWxsX3RleHQgcmlnaHQiPjIxOjM4PC90ZD4gLS0+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QndCw0LzQtdC90L7QstCw0L3QuNC1INC+0L/QtdGA0LDRhtC40Lg8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDQn9C10YDQtdCy0L7QtCDQtNC70Y8g0L/QvtC/0L7Qu9C90LXQvdC40Y8g0LHQsNC90LrQvtCy0YHQutC+0LPQviDRgdGH0LXRgtCwINCSINCQ0J4gItCa0YDQtdC00LjRgiDQldCy0YDQvtC/0LAg0JHQsNC90LoiCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIGtlYk1ldGFkYXRhP2hhc19jb250ZW50PgoKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIGtlYk1ldGFkYXRhLmN1c3RvbWVyX2luaXRpYWxzPz8+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0J/Qu9Cw0YLQtdC70YzRidC40Lo8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAke2tlYk1ldGFkYXRhLmN1c3RvbWVyX2luaXRpYWxzfQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBrZWJNZXRhZGF0YS5hY2NvdW50Pz8+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0J3QvtC80LXRgCDRgdGH0LXRgtCwINC/0L7Qu9GD0YfQsNGC0LXQu9GPPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPiR7a2ViTWV0YWRhdGEuYWNjb3VudH08L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBrZWJNZXRhZGF0YS5mZWVfdHlwZSA9PSAxPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QmtC+0LzQuNGB0YHQuNC40Y88L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+I3trZWJNZXRhZGF0YS5mZWVfYW1vdW50LzEwMDsgbTJ9ICR7cGF5bWVudFBheWVyLmN1cnJlbmN5fTwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8LyNpZj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIGtlYk1ldGFkYXRhLm9yaWdpbmFsX2Ftb3VudD8/PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCh0YPQvNC80LAg0L/QtdGA0LXQstC+0LTQsDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0Ij4je2tlYk1ldGFkYXRhLm9yaWdpbmFsX2Ftb3VudC8xMDA7IG0yfSAke3BheW1lbnRQYXllci5jdXJyZW5jeX08L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvI2lmPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPkVtYWlsPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGEgY2xhc3M9ImxpbmsiIGhyZWY9Im1haWx0bzoke3BheW1lbnRQYXllci50b1JlY2VpdmVyfSI+JHtwYXltZW50UGF5ZXIudG9SZWNlaXZlcn08L2E+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwjaWYgcGF5bWVudFBheWVyLmNhcmRUeXBlPz8+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0KHQv9C+0YHQvtCxINC+0L/Qu9Cw0YLRizwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD48L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIHBheW1lbnRQYXllci5jYXJkVHlwZT8/PiDQmtCw0YDRgtCwICR7cGF5bWVudFBheWVyLmNhcmRUeXBlfQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGJyPiAqKioqICR7cGF5bWVudFBheWVyLmNhcmRNYXNrUGFufQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8LyNpZj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlcl9yaWdodCByaWdodCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIHBheW1lbnRQYXllci5jYXJkVHlwZT8/PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGltZyBoZWlnaHQ9IjMycHgiIHdpZHRoPTU2cHgiIHNyYz0iaHR0cDovL3Jiay5tb25leS9tYWlsL2ltZy9pY29ucy8ke3BheW1lbnRQYXllci5jYXJkVHlwZX0ucG5nIiBhbHQ9IlBheW1lbnQgc3lzdGVtIGxvZ28iIGNsYXNzPSJwYXltZW50X3N5c3RlbSAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8LyNpZj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90YWJsZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0ic3VwcG9ydF9jb250YWluZXIgIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxwIGNsYXNzPSJzdXBwb3J0X3RleHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgINCd0LXQsdCw0L3QutC+0LLRgdC60LDRjyDQutGA0LXQtNC40YLQvdCw0Y8g0L7RgNCz0LDQvdC40LfQsNGG0LjRjyAi0K3Qu9C10LrRgtGA0L7QvdC90YvQuSDQv9C70LDRgtC10LbQvdGL0Lkg0YHQtdGA0LLQuNGBIiAo0J7QsdGJ0LXRgdGC0LLQviDRgSDQvtCz0YDQsNC90LjRh9C10L3QvdC+0Lkg0L7RgtCy0LXRgdGC0LLQtdC90L3QvtGB0YLRjCkKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDQsNC00YDQtdGBINC80LXRgdGC0L7QvdCw0YXQvtC20LTQtdC90LjRjzogMTE1MDkzPyDQnNC+0YHQutCy0LAsINGD0LsuINCf0LDQstC70L7QstGB0LrQsNGPLCDQtC43LCDRgdGC0YAuMSwg0JjQndCdIDc3NTAwMDU3MDAsINCR0JjQmiAwNDQ1MjUzMTMsCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg0L7RgdGD0YnQtdGB0YLQstC70Y/QtdGCINGB0LLQvtGOINC00LXRj9GC0LXQu9GM0L3QvtGB0YLRjCDQvdCwINC+0YHQvdC+0LLQsNC90LjQuCDQu9C40YbQtdC90LfQuNC4INCR0LDQvdC60LAg0KDQvtGB0YHQuNC4IOKEliAzNTA5LdCaINC+0YIgMTEuMDIuMjAxM9CzLAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgINGB0LDQudGCCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGEgaHJlZj0iaHR0cHM6Ly9yYmsubW9uZXkiIGNsYXNzPSJzdXBwb3J0X2xpbmsiPmh0dHBzOi8vcmJrLm1vbmV5PC9hPi4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvcD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxwIGNsYXNzPSJzdXBwb3J0X3RleHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgINCV0YHQu9C4INC+0L/QtdGA0LDRhtC40Y8g0YHQvtCy0LXRgNGI0LXQvdCwINC90LUg0LLQsNC80LgsINC90LDQv9C40YjQuNGC0LUg0YHQvtC+0YLQstC10YLRgdGC0LLRg9GO0YnQtdC1INC30LDRj9Cy0LvQtdC90LjQtSDQvdCwINC90LDRiNGDINGN0LvQtdC60YLRgNC+0L3QvdGD0Y4g0L/QvtGH0YLRgwogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxhIGhyZWY9Im1haWx0bzpzdXBwb3J0QHJiay5tb25leSAiIGNsYXNzPSJzdXBwb3J0X2VtYWlsX2xpbmsgIj5zdXBwb3J0QHJiay5tb25leTwvYT4uCiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3A+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8cCBjbGFzcz0ic3VwcG9ydF90ZXh0Ij4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDQotC10LvQtdGE0L7QvSDQtNC70Y8g0YHQv9GA0LDQstC+0Log0L/QviDQstC+0L/RgNC+0YHQsNC8INC/0YDQvtCy0LXQtNC10L3QuNGPINC/0LvQsNGC0LXQttC10LkgOCA4MDAgMjAwLTg4LTIwCiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3A+CiAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgIDwvY2VudGVyPgogICAgICAgIDwvdGQ+CiAgICA8L3RyPgo8L3RhYmxlPgo8L2JvZHk+CjwvaHRtbD4K', 'base64'), 'UTF8'));

update dudos.merchant_shop_bind set template_id = 12 where template_id = 11;
