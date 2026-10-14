axiom-web/
├── .env.example
├── .env.local
├── next.config.mjs
├── package.json
├── tsconfig.json
│
├── public/
│   └── fonts/
│
└── src/
    ├── app/                                       # Next.js App Router (Routing, Layouts, Server Shells)
    │   ├── api/                                   # Next.js Route Handlers (BFF / Edge proxy / Webhooks)
    │   │   └── export/
    │   │       └── [jobId]/route.ts               # Proxy download stream from ExportDocumentPort
    │   ├── (auth)/                                # Route Group: Auth Pages
    │   │   ├── login/
    │   │   │   └── page.tsx
    │   │   └── layout.tsx
    │   ├── (dashboard)/                           # Route Group: Authenticated Enterprise Workspace
    │   │   ├── layout.tsx                         # Injects Sidebar, UserNav, Global Command Palette
    │   │   ├── page.tsx                           # Financial Overview / Metrics Redirect
    │   │   ├── accounts/                          # Account Aggregate Frontend
    │   │   │   ├── page.tsx                       # RSC: Prefetches tree/flat view (Query Side)
    │   │   │   ├── [accountId]/
    │   │   │   │   └── page.tsx                   # Account Ledger drill-down
    │   │   │   └── loading.tsx
    │   │   ├── vouchers/                          # Operational Document Aggregate Frontend
    │   │   │   ├── page.tsx                       # RSC: Paginated Voucher Registry
    │   │   │   ├── new/
    │   │   │   │   └── page.tsx                   # Draft Voucher interactive builder
    │   │   │   └── [voucherId]/
    │   │   │       └── page.tsx                   # Voucher lifecycle detail (Post/Reverse view)
    │   │   ├── ledger/                            # General Ledger (Immutable Core) Frontend
    │   │   │   ├── trial-balance/
    │   │   │   │   └── page.tsx                   # Tabular high-volume multi-currency grid
    │   │   │   └── transactions/
    │   │   │       └── page.tsx
    │   │   └── reports/                           # Asynchronous Reporting Frontend
    │   │       ├── page.tsx                       # Job status polling & document download hub
    │   │       └── loading.tsx
    │   ├── error.tsx                              # Next.js Root Error Boundary
    │   ├── not-found.tsx
    │   └── layout.tsx                             # HTML Shell, Theme Provider, Query Client Provider
    │
    ├── domain/                                    # PURE TYPESCRIPT DOMAIN KERNEL (Zero React/Next.js dependencies)
    │   ├── common/
    │   │   ├── entity-id.ts                       # Strongly-typed brand IDs (AccountId, VoucherId)
    │   │   └── result.ts                          # Functional Result<T, E> error handling
    │   ├── monetary/                              # Mirror of Java domain.monetary Shared Kernel
    │   │   ├── money.ts                           # Value Object: Big.js / Dinero.js banker's rounding HALF_EVEN
    │   │   ├── currency.ts                        # Precision specifications & ISO-4217 constants
    │   │   └── exchange-rate.ts                   # FX conversion calculations
    │   └── accounting/
    │       ├── account-types.ts                   # ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
    │       ├── debit-credit.ts                    # DEBIT, CREDIT balancing rules
    │       └── voucher-validator.ts               # Client-side zero-sum invariant check before submitting command
    │
    ├── features/                                  # DOMAIN-ALIGNED FEATURE MODULES (Matches Java Aggregates)
    │   ├── account/
    │   │   ├── components/                        # UI Presentation Components
    │   │   │   ├── account-tree-view.tsx          # Virtualized recursive account tree
    │   │   │   ├── create-account-dialog.tsx      # Modal wrapping command form
    │   │   │   └── account-status-badge.tsx
    │   │   ├── hooks/                             # Feature-specific custom hooks
    │   │   │   ├── use-account-tree.ts            # Client-side tree expansion & filtering
    │   │   │   └── use-account-mutation.ts        # React Query / Action wrappers
    │   │   ├── server/                            # Server Orchestration (CQRS split)
    │   │   │   ├── account-queries.ts             # Calls Backend GET /api/v1/accounts (bypasses aggregates)
    │   │   │   └── account-actions.ts             # Next Server Actions (POST/PUT commands with Idempotency-Key)
    │   │   └── types/                             # Mirrored from Backend Application DTOs
    │   │       ├── account-commands.ts            # CreateAccountRequest, DeactivateAccountRequest
    │   │       └── account-views.ts               # AccountTreeNodeResponse, AccountResponse
    │   │
    │   ├── voucher/
    │   │   ├── components/
    │   │   │   ├── voucher-line-table.tsx         # Real-time balanced journal grid (DEBIT == CREDIT)
    │   │   │   ├── voucher-actions-toolbar.tsx    # Post / Reverse trigger buttons
    │   │   │   └── fx-rate-override-input.tsx
    │   │   ├── hooks/
    │   │   │   └── use-voucher-calculator.ts      # Computes multi-currency totals on every line change
    │   │   ├── server/
    │   │   │   ├── voucher-queries.ts             # Fetches voucher registry & views
    │   │   │   └── voucher-actions.ts             # PostVoucherCommand, ReverseVoucherCommand actions
    │   │   └── types/
    │   │       ├── voucher-commands.ts
    │   │       └── voucher-views.ts
    │   │
    │   ├── ledger/
    │   │   ├── components/
    │   │   │   ├── trial-balance-grid.tsx         # High-performance data grid (TanStack Table)
    │   │   │   └── snapshot-summary-card.tsx
    │   │   ├── server/
    │   │   │   └── ledger-queries.ts              # Fetches TrialBalanceView
    │   │   └── types/
    │   │       └── ledger-views.ts
    │   │
    │   └── report/
    │       ├── components/
    │       │   ├── job-status-poller.tsx          # Reactive status checking (SSE or periodic polling)
    │       │   └── export-dialog.tsx
    │       ├── server/
    │       │   ├── report-queries.ts              # GetJobStatusQuery
    │       │   └── report-actions.ts              # EnqueueReportJobCommand
    │       └── types/
    │           └── report-types.ts
    │
    ├── components/                                # CROSS-CUTTING DESIGN SYSTEM (Generic UI)
    │   ├── ui/                                    # Primitives (Radix / Shadcn / Tailwind)
    │   │   ├── button.tsx
    │   │   ├── dialog.tsx
    │   │   ├── data-table.tsx
    │   │   ├── input.tsx
    │   │   └── toast.tsx
    │   └── feedback/
    │       ├── problem-detail-alert.tsx           # Formatter for RFC 7807 error responses
    │       └── loading-skeletons.tsx
    │
    ├── lib/                                       # SHARED TECHNICAL INFRASTRUCTURE
    │   ├── http/
    │   │   ├── api-client.ts                      # Fetch wrapper with Auth header injection & retry logic
    │   │   ├── rfc7807-error.ts                   # ProblemDetail class (maps backend HTTP 4xx/5xx responses)
    │   │   └── idempotency.ts                     # Generates UUIDv4 for "Idempotency-Key" header on commands
    │   ├── auth/
    │   │   ├── session.ts                         # Edge JWT validation & cookie store adapter
    │   │   └── permissions.ts                     # UI role guard (ADMIN, ACCOUNTANT, AUDITOR)
    │   └── validation/
    │       └── zod-schemas.ts                     # Transport validation matching backend DTO constraints
    │
    └── middleware.ts                              # Edge Middleware: Guard protected routes & JWT verification
